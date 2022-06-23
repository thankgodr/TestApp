# TestApp

### **INSTALLATION**
---

**Step 1.** Add the JitPack repository to your build file

 - Add it in your root build.gradle at the end of repositories:
```
allprojects{
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

**Step 2.** Add the dependency
```
dependencies {
  ....
  implementation 'com.github.thankgodr:TestApp:master-SNAPSHOT'
}
```

### **USAGE**
---
To extract text from Image. 
```
VerifySDK.getIdDetails(bitmap).onEach { resource ->
  when(resource){
    is Resource.Loading ->{ //Do stuff }
    is Resource.Success ->{ Log.i(TAG, resource.data.data) }
    is is Resource.Error -> { // Do stuff }
  }.launchIn(coroutineScope)
```
*the getIdDetails function was overloaded to allow other kinds of document like File and Uri. but would require a context.*

To extract detect face on the ID and verify only one face is on the ID.
```
VerifySDK.verifyFaceId(bitmap).onEach { resource ->
  when(resource){
    is Resource.Loading ->{ //Do stuff }
    is Resource.Success ->{ //Do stuff with the returned bitmap }
    is is Resource.Error -> { // Do stuff }
  }.launchIn(coroutineScope)
```
*the verifyFaceId function was overloaded to allow other kinds of document like File and Uri. but would require a context.*


 
