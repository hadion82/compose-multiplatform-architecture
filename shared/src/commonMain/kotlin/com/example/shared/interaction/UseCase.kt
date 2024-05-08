package com.example.shared.interaction

interface UseCase<in P, out T> where T : Any {

    fun execute(params: P): T

    operator fun invoke(
        params: P
    ) = try {
        execute(params).let {
            Result.success(it)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Result.failure(e)
    }
}
