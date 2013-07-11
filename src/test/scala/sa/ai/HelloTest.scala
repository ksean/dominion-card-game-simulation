package sa.ai

import org.specs2.mutable.{SpecificationWithJUnit}


/**
 * Sample
 */
class HelloTest extends SpecificationWithJUnit  {

  "World world model" should {
    val worldModel = WorldSingleton.model

    "Have value 'world'" in {
      worldModel.value must be equalTo "world"
    }
  }

}
