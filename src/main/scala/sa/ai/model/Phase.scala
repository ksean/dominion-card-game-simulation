package sa.ai.model

/**
 * Dominion phase
 * The phases determine what stage of a player turn the state of the game is in
 * There are 3 phases: Action, Buy, Cleanup
 */
sealed trait Phase
case object BeforeTheGame extends Phase
case object Action extends Phase
case object Buy extends Phase
case object Cleanup extends Phase