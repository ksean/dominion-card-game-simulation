package sa.ai.model

/**
 * Dominion phase
 * The phases determine what stage of a player turn the state of the game is in
 * There are 3 phases: Action, Buy, Cleanup
 */
sealed trait Phase
case object BeforeTheGame
case object Action
case object Buy
case object Cleanup