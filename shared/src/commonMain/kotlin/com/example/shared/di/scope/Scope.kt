package com.example.shared.di.scope

import me.tatarka.inject.annotations.Scope

@Scope
@Target(AnnotationTarget.CLASS,  AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class SingleToneScope

@Scope
@Target(AnnotationTarget.CLASS,  AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class ViewModelScope

@Scope
@Target(AnnotationTarget.CLASS,  AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class RootViewScope

@Scope
@Target(AnnotationTarget.CLASS,  AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class FragmentViewScope

