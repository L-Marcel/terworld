package ufrn.imd.core.rendering;

import ufrn.imd.core.nodes.Player;

public class Hud {
  public String render(Player player, boolean won, int columns) {
    String status = this.getStatus(player, won);
    return status.substring(0, Math.max(0, Math.min(columns, status.length())));
  }

  private String getStatus(Player player, boolean won) {
    if (won)
      return "Vitoria! Todas as fases concluidas.";

    String status = "HP: " + player.getHp() + "/" + player.getMaxHp();

    if (player.isDead())
      return status + " | Derrota";
    if (player.hasCompletedLevel())
      return status + " | Fase concluida";
    if (player.isProtected())
      return status + " | Protegido";

    return status;
  }
}
