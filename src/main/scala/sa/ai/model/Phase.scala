package sa.ai.model

/**
 * Dominion phase
 * The phases determine what stage of a player turn the state of the game is in
 * There are 3 phases: Action, Buy, Cleanup
 */
sealed trait Phase

case object BeforeTheGamePhase extends Phase
case object ActionPhase        extends Phase
case object BuyPhase           extends Phase
case object CleanupPhase       extends Phase
case object AfterTheGamePhase  extends Phase

