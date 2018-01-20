package com.example.calvin.motiontracker.application.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * This class defines components used for injection at application level.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
@interface ApplicationScope {
}
