package com.github.frankfarrell.dynamodsl

import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import org.junit.Test

/**
 * Created by frankfarrell on 19/07/2018.
 */

class DynamoDSLTest {
    @Test
    fun `it should build nested filter queries correctly`(){
        val queryResult = DynamoDSL(AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build())
                .query("mytable") {
                    hashKey("myHashKey") {
                        eq(2)
                    }
                    sortKey("mysortkey"){
                        eq (2)
                        between ( 2 AND 3)
                    }
                    filtering {
                        attribute("a") {
                            eq(1)
                        } and attribute("b"){
                            eq(2)
                        } or {
                            attribute("c"){
                                eq(3)
                            } and attributeExists("d") or {
                                attribute("e"){
                                    eq(4)
                                }
                            }
                        } or attributeExists("f")

                    }
                }

        DynamoDSL(AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build()).query("c") {filtering {  }}

        if(queryResult.hasNext()){
            queryResult.next()
        }


    }


}

