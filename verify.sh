#!/bin/bash

set -euo pipefail

openjml --esc --timeout=60 \
  --smt-strings=false \
  src/main/java/ufrn/imd/core/Controls.java \
  src/main/java/ufrn/imd/core/physics/Grid.java \
  src/main/java/ufrn/imd/core/physics/Vector2.java \
  src/main/java/ufrn/imd/core/components/HealthComponent.java \
  src/main/java/ufrn/imd/core/components/JumpComponent.java \
  src/main/java/ufrn/imd/core/constants/Health.java \
  src/main/java/ufrn/imd/core/constants/Ticks.java \
  src/main/java/ufrn/imd/core/constants/Attacks.java \
  src/main/java/ufrn/imd/core/constants/Forces.java \
  src/main/java/ufrn/imd/core/constants/Jumps.java \
  src/main/java/ufrn/imd/core/constants/Levels.java \
  src/main/java/ufrn/imd/core/constants/Rendering.java \
  src/main/java/ufrn/imd/core/components/AttackComponent.java \
  src/main/java/ufrn/imd/core/components/MovementComponent.java \
  src/main/java/ufrn/imd/core/enums/AttackState.java \
  src/main/java/ufrn/imd/core/rendering/Camera.java \
  src/main/java/ufrn/imd/core/enums/Direction.java

openjml --esc --timeout=60 \
  --smt-strings=false \
  --source-path src/main/java \
  --method='ufrn.imd.core.abstractions.Node[.].*;ufrn.imd.core.abstractions.Node2D[.].*;ufrn.imd.core.abstractions.Tile[.].*;ufrn.imd.core.nodes.Block[.].*;ufrn.imd.core.nodes.Spike[.].*;ufrn.imd.core.nodes.EndOfLevel[.].*;ufrn.imd.core.abstractions.Fighter[.].*;ufrn.imd.core.nodes.Sword.Sword;ufrn.imd.core.nodes.Sword.getFacing;ufrn.imd.core.nodes.SwordTip.SwordTip;ufrn.imd.core.abstractions.Body2D.Body2D;ufrn.imd.core.abstractions.Body2D.jump;ufrn.imd.core.abstractions.Body2D.drop;ufrn.imd.core.abstractions.Body2D.setHorizontalSpeed;ufrn.imd.core.abstractions.Body2D.getVerticalSpeed;ufrn.imd.core.physics.Physics.getCollisionMap' \
  src/main/java/ufrn/imd/core/abstractions/Node.java \
  src/main/java/ufrn/imd/core/abstractions/Node2D.java \
  src/main/java/ufrn/imd/core/abstractions/Tile.java \
  src/main/java/ufrn/imd/core/nodes/Block.java \
  src/main/java/ufrn/imd/core/nodes/Spike.java \
  src/main/java/ufrn/imd/core/nodes/EndOfLevel.java \
  src/main/java/ufrn/imd/core/abstractions/Fighter.java \
  src/main/java/ufrn/imd/core/nodes/Sword.java \
  src/main/java/ufrn/imd/core/nodes/SwordTip.java \
  src/main/java/ufrn/imd/core/abstractions/Body2D.java \
  src/main/java/ufrn/imd/core/physics/Physics.java
