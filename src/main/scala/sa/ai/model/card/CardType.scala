package sa.ai.model.card

sealed trait CardType

case object Treasure   extends CardType
case object Victory    extends CardType
case object ActionType extends CardType // Type suffix to disambiguate from ActionPhase
case object CurseType  extends CardType
