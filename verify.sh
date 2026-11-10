#!/bin/bash

openjml --esc --timeout=10 --progress \
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
  src/main/java/ufrn/imd/core/constants/Rendering.java