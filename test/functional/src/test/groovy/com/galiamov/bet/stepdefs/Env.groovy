package com.galiamov.bet.stepdefs

import com.galiamov.bet.world.WorldWithDependencies
import com.netflix.governator.guice.LifecycleInjector

import static cucumber.api.groovy.Hooks.World

World {

    def lifecycleInjector = LifecycleInjector.builder()
            .usingBasePackages("com.galiamov")
            .build()

    def injector = lifecycleInjector.createInjector()

    def world = injector.getInstance(WorldWithDependencies)
    world
}