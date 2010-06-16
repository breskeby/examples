package com.breskeby.example

import org.codehaus.groovy.transform.GroovyASTTransformationClass
import java.lang.annotation.ElementType
import java.lang.annotation.Target
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention

/**
 * Created by IntelliJ IDEA.
 * User: Rene
 * Date: 08.06.2010
 * Time: 23:25:30
 * To change this template use File | Settings | File Templates.
 */
@Retention (RetentionPolicy.SOURCE)
@Target ([ElementType.METHOD])
@GroovyASTTransformationClass (["com.breskeby.example.CachedTransformation"])
@interface Cached {

}
