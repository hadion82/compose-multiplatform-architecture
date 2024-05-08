# compose-multiplatform-architecture
Project to support multiple platforms with one code
The existing architecture and compose respositories have been deleted. 
Instead it will fallback to this repository

## Architecture
Multiplatform Compose + MVI
When events and effects occur due to user interaction, they are delivered to the processor.
Processor processes business logic and updates changed UI state

As shown below, Events are processed synchronously and Effects are processed asynchronously.


Muliplatform Compose + MVVM

## Chagned libraries for multi-platform compatibility

Database - Room -> SqlDelight

API - Retrofit -> Ktor

Image - Glide -> Coil

Design - Meterial -> Meterial3

DI - Dagger Hilt -> Kotlin Inject

Paging - Paging3 -> App cash Paging

Logger - Timber -> Napier
