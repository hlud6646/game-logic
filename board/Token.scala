package board

trait Token {
  def animal: String
  def orientation: Int
  def owner: Player
  def flip: Token
}
