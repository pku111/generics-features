Java Generics
===============

Sources
-------

* [Java Generics FAQ](http://www.angelikalanger.com/GenericsFAQ/JavaGenericsFAQ.html) by Angelika Langer
* [Java Generics Tutorial](https://docs.oracle.com/javase/tutorial/java/generics/index.html) by Oracle

Basics
------
*  Generics enable _types_ (classes and interfaces) to be parameters when defining classes, interfaces and methods.
 Like formal parameters in method declarations, type parameters provide a way to re-use the same code with 
 different inputs. The difference is that the inputs to formal parameters are values, while the inputs to type 
 parameters are types.
* `class Name<T1, T2, ..., Tn> { /* ... */ }` - between the angle brackets `<>` are _type parameters_ 
(or _type variables_)
* People tend to mix up _type parameters_ and _type arguments_ [Example](src/test/java/_01_Basic.java)
    * `class Name<T> {...}` - `T` is the _type parameter_
    * `private Name<String> field;` - `String` is the _type argument_
* Type parameter naming convention
    * Default java
        * `E` - Element (used extensively by the Java Collections Framework)
        * `K` - Key
        * `N` - Number
        * `T` - Type
        * `V` - Value
        * `S`, `U`, `V` etc. - 2nd, 3rd, 4th types
    * Lakitu
        * Capitalised, underscore separated full variable names
            * `ASSET`
            * `SELECTED_CONTENT_METADATA`
        * `E` is used for exceptions
        * It's acceptable to use `T`, or `E` if they are the only type parameters
* There is no hard limit on the number of type parameters a class or method can have
    * There is a derived limit from the way the class information is stored in memory. 
    This allows ~2000-10000 type parameters.
* Since Java 7 some type parameters can be inferred by the compiler. To do this use the _diamond_ `<>`
 [Example](src/test/java/_02_TypeInference.java)
* Never use raw types, use _wildcards_ `<?>`
* Generic methods are methods that introduce their own type parameters. `public <T> T transform(T input) {...}`
* `ListBuilder.<String>list().add()` or `new ListBuilder<String>().<String>add()` - _Type witness_
* Generic type parameters should not expose internals of the class. 
Only create a generic type parameter for types used on the class interface. 
I.e. method parameters and method response types.

Bounds, inheritance and subtyping
---------------------------------
* `class ClassName<T extends SomeClass> {...}` - _bounded type parameter_ - can call methods defined on the bounds
* Multiple bounds are possible: `class Name<T extends A & B & C>` - class first (if any) then interfaces 

* Given two concrete types `A` and `B`, `SomeClass<A>` has no relationship to `SomeClass<B>`, 
regardless of whether or not `A` and `B` are related. The common parent of `SomeClass<A>` and `SomeClass<B>` 
is `Object`. (For example, `Integer` is a `Number` but `List<Integer>` is not a `List<Number>`) 
[Example](src/test/java/_04_Bounds.java)

WildCards
---------
* `?` is a wildcard, represents an unknown type
* Can be used as type of a
    * parameter
    * field
    * local variable
    * return type - try to avoid doing this, because it forces the user to deal with wildcards
* The wildcard is never used as a type argument for a 
    * generic method invocation
    * generic class instance creation
    * supertype.
* Upper Bounded Wildcards `<? extends SomeClass>` - means extends or implements
    * Restricts the unknown type to be a specific type or a subtype of that type 
[Example](src/test/java/_05_UpperBoundedWildcards.java)
* Unbounded Wildcards `<?>` - use when
    * The functionality required is available in the `Object` class
    * When the code is using methods in the generic class that don't depend on the type parameter
    * For any concrete type `A`, `List<A>` is a subtype of `List<?>`
    * `List<Object>` and `List< ? >` are not the same. You can insert an `Object`, or any subtype of `Object`, 
        into a `List<Object>`. But you can only insert `null` into a `List<?>` 
        [Example](src/test/java/_06_UnboundedWildcards.java)
* Lower Bounded WildCards `<? super SomeClass>`
    * Restricts the unknown type to be a specific type or a super type of that type.
    * Mostly used for "out" variables
* Can't define both lower and upper bounds, only one of them
* [Wildcards and Subtyping](https://docs.oracle.com/javase/tutorial/java/generics/subtyping.html)
[Example](src/test/java/_08_WildcardsAndSubtyping)
* _Wildcard capture_
    * Sometimes the compiler can figure out the type of the wildcard
    * Error message with `capture of`
    * `?` do not match
    * Wildcard capturing helper methods using type inference are named `originalMethodNameHelper` 
    [Example](src/test/java/_09_WildcardCapture.java)
* Guidelines for Wildcard Use
    * An "in" variable is defined with an upper bounded wildcard, using the extends keyword.
    * An "out" variable is defined with a lower bounded wildcard, using the super keyword.
    * In the case where the "in" variable can be accessed using methods defined in the Object class, 
        use an unbounded wildcard.
    * In the case where the code needs to access the variable as both an "in" and an "out" variable, 
        do not use a wildcard.
    * Upper bounded Lists tend to end up mostly read only by nature
        * You can add null.
        * You can invoke clear.
        * You can get the iterator and invoke remove.
        * You can capture the wildcard and write elements that you've read from the list.

Type erasure
------------
* Generic are not available at runtime
* The compiler applies type erasure to
    * Replace all type parameters in generic types with their bounds or Object if the type parameters are unbounded. The produced bytecode, therefore, contains only ordinary classes, interfaces, and methods.
    * Insert type casts if necessary to preserve type safety.
    * Generate [bridge methods](https://docs.oracle.com/javase/tutorial/java/generics/bridgeMethods.html) 
        to preserve polymorphism in extended generic types.
* Non-Reifiable Types
    * A `reifiable type` is a type whose type information is fully available at runtime. This includes primitives, 
    non-generic types, raw types, and invocations of unbound wildcards.
    * `Non-reifiable types` are types where information has been removed at compile-time by type erasure
    * `Heap pollution` occurs when a variable of a parameterized type refers to an object that is not of that parameterized type.
    * Generic methods that include vararg input parameters can cause heap pollution. [Example](src/test/java/_10_HeapPollution)
    * `@SafeVarargs`
        * Prevents warnings from `T...` or `SomeClass<T>...`
        * Only on static and non-constructor method declarations
        * You need to make sure that the body of the method does not throw a ClassCastException or other similar 
            exception due to improper handling of the varargs formal parameter

Restrictions
------------
* Cannot Instantiate Generic Types with Primitive Types - but during usage autoboxing works
* Cannot Create Instances of Type Parameters - but reflection works
* Cannot Declare Static Fields Whose Types are Type Parameters - generics are instance bound
* Cannot Use Casts (mostly) or instanceof With Parameterized Types - but wildcards work (e.g. `list instanceof ArrayList<?>`)
* Cannot Create Arrays of Parameterized Types e.g. `List<Integer>[] arrayOfLists = new List<Integer>[2];`
* Cannot Create, Catch, or Throw Objects of Parameterized Types
    * Can not
        * `class MathException<T> extends Exception {...}`
        * `class QueueFullException<T> extends Throwable {...}`
        * `} catch (T e) {`
    * But you can throw T `public void parse(File file) throws T`
* Cannot Overload a Method Where the Formal Parameter Types of Each Overload Erase to the Same Raw Type
    ```
    public class Example {
         public void print(Set<String> strSet) { }
         public void print(Set<Integer> intSet) { }
     }
    ```
    
TDD with generics
-----------------
* We rarely write generic code from scratch
* Mostly refactor existing duplicated code to use generics
* When testing a generic class use the generic superclass for all type arguments
    * Instead of a randomly picked concrete type
    * This prevents the code breaking when somebody modifies the concrete type for unrelated reasons. 
        The code you are testing is generic, hence it does not care which concrete implementation it's working on.
    * Use stubs for interfaces and non-instantiatable classes.
    * Defaults help
    * Dummies help, they support generics (`TypeRef`)
    * See `CompositeTransformer` in lakitu 
        * `T` is genric with no bounds -> dummy object on the `T`
    * See `UriTransformingTitleMetadataSelectionServiceTest` in lakitu 
        * `CONTENT_OPERATION` is generic -> dummy ContentOperation on the generic `CONTENT_OPERATION extends ContentOperations`
    * See `ChannelMetadataSelectionServiceTest` in lakitu 
        * all generic parameters defined / 'locked in' -> concrete defaults
* Exception to the above when testing generic classes with generic type parameters that have no bounds (`<T>`) 
    and the type parameter is directly used in the class
    * Pick a few simple types and test the code with all of them
        * `String`
        * `Integer` (use `int` in tests, autoboxing will make it easier to understand)
    * See `SelectionTest` in lakitu
   
    
Specific examples
-----------------
* Type argument resolving during runtime
    * `TypeRef` or `TypeReference` or `TypeLiteral` - guice, jersey, etc
    * `bind(new TypeLiteral<SelectedContentMetadataProvider<SelectedTitleMetadata, EventPlayout>>() {}).to(new 
    TypeLiteral<SelectedTitleMetadataProvider<EventPlayout>>() {})`;
    * bind `bind(new TypeLiteral<Transformer<Title>>() {}).to(GbAndIeTitleEndpointUriPrefixService.class)`;
    * [Example](src/test/java/examples/_01_TypeArgumentsAtRuntime.java)
* Generic type argument propagation [Example](src/test/java/examples/_02_GenericTypePropagation.java)
* Generic class chain [Example](src/test/java/examples/_03_GenericClassChain.java)
* Builders [Example](src/test/java/examples/_04_Builders.java)
* Extending 'overlapping' generic classes `class A<T> extends I<T, K> {...}` 
    [Example1](src/test/java/examples/_05_GenericClassExtendingGenericClassMoreSpecific.java)
    [Example2](src/test/java/examples/_06_GenericClassExtendingGenericClassLessSpecific.java)
* Injecting lambda instead of an instance of a generic class [Example](src/test/java/examples/_07_LambdaInjection.java)
* Don't use generics when you don't need to, unless they help readability (as any other language feature)
    * E.g. PlayService in Video.  
    * [Example1](src/test/java/examples/_08_WhenNotToUseGenerics.java)
    * [Example2](src/test/java/examples/_09_WhenNotToUseGenericsWithGenerics.java)
* LambdaExceptionUtils
    * Method that takes `Function<T, R>` can not take `FunctionThatThrows<T, R> throws E`
        * The thrown Exception is just as important as the parameter or the return type
    * Issue: A lot of streaming interfaces work with `Supplier`, `Consumer`, `Function` and `Predicate`
        * They do not throw checked exception
        * We'd like to use functions that throw checked exceptions
    * [Example](src/test/java/examples/_10_LambdaExceptionUtils.java)


* Generic method - `private static <T, E extends Exception> List<T> listOrThrowIfEmpty(List<T> list, E exception) throws E {...}`


   



 
