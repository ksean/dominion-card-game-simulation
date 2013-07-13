package sa.ai.model

/**
 * Dominion modification
 * A modification changes some aspect of the state of the game
 * e.g. A "wealth" modification of +1 to be used by a copper card
 */
case class Modification(
  mod_type : String // TODO: need to build out this definition more
)