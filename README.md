# loadlib4j
Little helper class for testing the loading of native libraries in Java.
It uses the `java.lang.System.loadLibrary(String)` call to load the library.

## Usage

### Command-line

The following command tries to load the `libmkl_rt.so` library on Linux,
which has to be on `LD_LIBRARY_PATH` path: 

```bash
java -jar loadlib4j-0.0.1.jar libmkl_rt.so
```

The output on stdout will be something like this:

```
libmkl_rt.so: false
libmkl_rt: false
mkl_rt: true
```

As you can see, loadlib4j also tries the library name without extension
and `lib` prefix. In the above example, the test succeeds with `mkl_rt`.

### Java

```java
import com.github.fracpete.loadlib4j.Main;
import java.util.Map;
...
// the libraries to test
String[] libs = new String[]{"libmkl_rt.so"};
Main test = new Main();
Map<String,Boolean> result = test.testLoading(libs);
// do something with the results, eg output them
for (String lib: result.keySet()) {
  System.out.println(lib + " -> " + result.get(lib));
}
```

## Maven

Use the following dependency to include the library in your Maven project:
```xml
    <dependency>
      <groupId>com.github.fracpete</groupId>
      <artifactId>loadlibs4j</artifactId>
      <version>0.0.1</version>
    </dependency>
```
