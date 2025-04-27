### **JAVA 17 (temurin)**
**<span style="color:green;">Ограничение количества CPU, доступных контейнеру</span>**

echo 'Runtime.getRuntime().availableProcessors()' | docker run --rm -i eclipse-temurin:17 jshell -q

echo 'Runtime.getRuntime().availableProcessors()' | docker run --rm -i --cpus=3 eclipse-temurin:17 jshell -q


**<span style="color:green;">Ограничение кочества RAM, доступных контейнеру. Если не указан явно размер хипа, то возбмется 1/4 от доступной памяти</span>**

docker run -it --rm eclipse-temurin:17 java -XX:+PrintFlagsFinal | grep "size_t MaxHeapSize"

docker run -it --rm -m=1024M eclipse-temurin:17 java -XX:+PrintFlagsFinal | grep "size_t MaxHeapSize"

docker run -it --rm -m=1024M eclipse-temurin:17 java -Xmx600m -XX:+PrintFlagsFinal -version | grep "size_t MaxHeapSize"


**<span style="color:green;">Создание массива, большего чем Max_Heap, ведет к ООМ</span>**

echo 'new byte[100_000_000]' | docker run -i --rm -m=1024M eclipse-temurin:17 jshell -q

echo 'new byte[500_000_000]' | docker run -i --rm -m=1024M eclipse-temurin:17 jshell -q


### **JAVA 8 (openjdk)**

**<span style="color:green;">Java 8 не учитывает ограничения по лимитам</span>**

docker run -it --rm openjdk:8 java -XX:+PrintFlagsFinal | grep "uintx MaxHeapSize"

docker run -it --rm -m=1024M openjdk:8 java -XX:+PrintFlagsFinal | grep "uintx MaxHeapSize"

docker run -it --rm -m=1024M openjdk:17 java -XX:+PrintFlagsFinal | grep "size_t MaxHeapSize"

echo 'new byte[100_000_000]' | docker run -i --rm -m=1024M openjdk:8 jshell -q