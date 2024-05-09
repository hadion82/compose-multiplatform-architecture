# compose-multiplatform-architecture
Project to support multiple platforms with one code.

The existing architecture repository and compose respositories have been deleted.

It has been integrated into this repository instead.

## Architecture

### Multiplatform
Each modules can uses platform-specific API.

The Platform-specific APIs used are integrated into a common Kotlin API.

![multiplatform](https://github.com/hadion82/compose-multiplatform-architecture/assets/11436005/7395dcff-ae90-4443-9f02-713b9bfdd086)


### Clean Architecture

UI can only be changed through references to UiState.

To deliver user interaction results, only access is allowed through Presenter.

Data flows one way and only the layer immediately below has dependencies :

- UI Layer
- Domain Layer
- Data Layer



### Compose Multiplatform + MVI

When events and effects occur due to user interaction, they are delivered to the processor.

Processor processes business logic and updates changed UI state.

UI state is updated according to the Compose life cycle.

The operation of Event and Effect is as shown below :



#### Event

First-input, first-output structure ensures input/output order.

![event](https://github.com/hadion82/compose-multiplatform-architecture/assets/11436005/f4cdf65a-5113-4da4-8384-9ba658760fd7)



#### Effect

As an asynchronous task, it is output in the order in which the task is completed.

![effect](https://github.com/hadion82/compose-multiplatform-architecture/assets/11436005/b42c68f1-795f-4b02-89e6-40d347bef316)



#### Event + Effect

Concurrency and sequential task possible using Event and Effect.

![event_effect](https://github.com/hadion82/compose-multiplatform-architecture/assets/11436005/f51b4d03-e7d9-4f5b-a44c-e59a4ef1fad6)



### Compose Muliplatform + MVVM
It is necessary to create a view model that matches the view life cycle of each platform, Android and iOS.

UI updates should also be tailored to each platform's UI lifecycle.

Other than that, it is the same as the general MVVM pattern.


## Libraries
Many previously used libraries do not yet support multiplatform.

If necessary, you need to change to a compatible library. 

The libraries used in this project are as follows.

- Database - [SqlDelight](https://github.com/cashapp/sqldelight)

- Network - [Ktor](https://github.com/ktorio/ktor)

- Image - [Coil3](https://github.com/coil-kt/coil)

- Design - [Meterial3](https://android.googlesource.com/platform/frameworks/support/+/refs/heads/androidx-main/compose/material3/)

- DI - [kotlin-inject](https://github.com/evant/kotlin-inject)

- Paging - [App cash Paging](https://github.com/cashapp/multiplatform-paging)

- Logger - [Napier](https://github.com/AAkira/Napier)

- Build flavor - [BuildKonfig](https://github.com/yshrsmz/BuildKonfig)

- Datetime - [kotlin-datetime](https://github.com/Kotlin/kotlinx-datetime)

- Permission - [accompanist](https://github.com/google/accompanist)

- Crypto - [kotlincrypto](https://github.com/KotlinCrypto/core)

- Number - [ionspin](https://github.com/ionspin/kotlin-multiplatform-bignum)
