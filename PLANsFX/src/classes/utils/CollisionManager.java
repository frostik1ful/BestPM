package classes.utils;

import classes.ClientPlayer;
import classes.ServerPlayer;
import classes.planes.Plane;

import java.util.LinkedList;
import java.util.List;

public class CollisionManager {
    private List<HitBox> hitBoxes = new LinkedList<>();

    private List<HitBox> bullets = new LinkedList<>();
    private List<HitBox> planeBodys = new LinkedList<>();
    private List<HitBox> planeNoses = new LinkedList<>();
    private List<HitBox> planeHeads = new LinkedList<>();
    private List<HitBox> rockets = new LinkedList<>();
    private List<HitBox> parachutes = new LinkedList<>();
    private List<HitBox> humans = new LinkedList<>();
    private List<HitBox> airDrops = new LinkedList<>();
    private List<HitBox> grounds = new LinkedList<>();
    private List<HitBox> scraps = new LinkedList<>();

    private List<HitBox> hitBoxesToRemove = new LinkedList<>();

    public void checkCollision() {
        bullets.forEach(bullet -> {
            if (bullet.isAlive()) {
                grounds.forEach(ground -> {
                    if (bullet.isIntersects(ground)) {
                        bullet.addCollision(ground);
                    }
                });
                planeBodys.forEach(planeBody -> {
                    if (bullet.isIntersects(planeBody)) {
                        bullet.addCollision(planeBody);
                        planeBody.addCollision(bullet);
                    }
                });
                planeNoses.forEach(planeNose -> {
                    if (bullet.isIntersects(planeNose)) {
                        bullet.addCollision(planeNose);
                        planeNose.addCollision(bullet);
                    }
                });
                planeHeads.forEach(planeHead -> {
                    if (bullet.isIntersects(planeHead)) {
                        ServerPlayer.col.setText(ServerPlayer.col.getText()+" \n "+"bullet to headDamage");
                        ClientPlayer.col.setText(ServerPlayer.col.getText()+" \n "+"bullet to headDamage");
                        bullet.addCollision(planeHead);
                        ((Plane) planeHead.getHitBoxParent()).addHeadShot();
                        //planeHead.addCollision(bullet);
                    }
                });
                humans.forEach(human -> {
                    if (bullet.isIntersects(human)) {
                        bullet.addCollision(human);
                        human.addCollision(bullet);
                    }
                });
                parachutes.forEach(parachute -> {
                    if (bullet.isIntersects(parachute)) {
                        bullet.addCollision(parachute);
                        parachute.addCollision(bullet);
                    }
                });
                airDrops.forEach(airDrop -> {
                    if (bullet.isIntersects(airDrop)) {
                        bullet.addCollision(airDrop);
                    }
                });
                rockets.forEach(rocket -> {
                    if (bullet.isIntersects(rocket)) {
                        bullet.addCollision(rocket);
                        rocket.addCollision(bullet);
                    }
                });

                if (!bullet.isAlive()) {
                    hitBoxesToRemove.add(bullet);
                }
            } else {
                hitBoxesToRemove.add(bullet);
            }
        });

        removeDeadHitboxes(bullets);


        rockets.forEach(rocket -> {
            if (rocket.isAlive()) {
                grounds.forEach(ground -> {
                    if (rocket.isIntersects(ground)) {
                        rocket.addCollision(ground);
                    }
                });

                planeBodys.forEach(planeBody -> {
                    if (rocket.isIntersects(planeBody)) {
                        rocket.addCollision(planeBody);
                        planeBody.addCollision(rocket);
                    }
                });
                planeNoses.forEach(planeNose -> {
                    if (rocket.isIntersects(planeNose)) {
                        rocket.addCollision(planeNose);
                        planeNose.addCollision(rocket);
                    }
                });
//                planeHeads.forEach(planeHead -> {
//                    if (rocket.isIntersects(planeHead)) {
//                        rocket.addCollision(planeHead);
//                        ((Plane) planeHead.getHitBoxParent()).addHeadShot();
//                        //planeHead.addCollision(rocket);
//                    }
//                });
                humans.forEach(human -> {
                    if (rocket.isIntersects(human)) {
                        rocket.addCollision(human);
                        human.addCollision(rocket);
                    }
                });
                parachutes.forEach(parachute -> {
                    if (rocket.isIntersects(parachute)) {
                        parachute.addCollision(rocket);
                    }
                });
                airDrops.forEach(airDrop -> {
                    if (rocket.isIntersects(airDrop)) {
                        rocket.addCollision(airDrop);
                    }
                });
                rockets.forEach(rocket1 -> {
                    if (rocket != rocket1 && rocket.isIntersects(rocket1)) {
                        rocket.addCollision(rocket1);
                        rocket1.addCollision(rocket);
                    }
                });
            } else {
                hitBoxesToRemove.add(rocket);
            }

        });
        removeDeadHitboxes(rockets);


        planeBodys.forEach(planeBody -> {
            if (planeBody.isAlive()) {
                grounds.forEach(ground -> {
                    if (planeBody.isIntersects(ground)) {
                        planeBody.addCollision(ground);
                    }
                });
            } else {
                hitBoxesToRemove.add(planeBody);
            }

        });
        removeDeadHitboxes(planeBodys);

        planeNoses.forEach(planeNose -> {
            if (planeNose.isAlive()) {
                grounds.forEach(ground -> {
                    if (planeNose.isIntersects(ground)) {
                        planeNose.addCollision(ground);
                    }
                });
                humans.forEach(human -> {
                    if (planeNose.isIntersects(human)) {
                        human.addCollision(planeNose);
                    }
                });
                parachutes.forEach(parachute -> {
                    if (planeNose.isIntersects(parachute)) {
                        parachute.addCollision(planeNose);
                    }
                });
                airDrops.forEach(airDrop -> {
                    if (planeNose.isIntersects(airDrop)) {
                        planeNose.addCollision(airDrop);
                        airDrop.addCollision(planeNose);
                    }
                });
            } else {
                hitBoxesToRemove.add(planeNose);
            }

        });
        removeDeadHitboxes(planeNoses);

        planeHeads.forEach(planeHead -> {
            if (!planeHead.isAlive()) {
                hitBoxesToRemove.add(planeHead);
            }
        });
        removeDeadHitboxes(planeHeads);

        humans.forEach(human -> {
            if (human.isAlive()) {
                grounds.forEach(ground -> {
                    if (human.isIntersects(ground)) {
                        human.addCollision(ground);
                    }
                });
                airDrops.forEach(airDrop -> {
                    if (human.isIntersects(airDrop)) {
                        //System.out.println("HUMAN INTERSECTS AIRDROP "+human.getHitBoxParent().getClass());
                        human.addCollision(airDrop);
                        airDrop.addCollision(human);
                    }
                });
            } else {
                hitBoxesToRemove.add(human);
            }
        });
        removeDeadHitboxes(humans);


        airDrops.forEach(airDrop -> {
            if (airDrop.isAlive()) {
                grounds.forEach(ground -> {
                    if (airDrop.isIntersects(ground)) {
                        airDrop.addCollision(ground);

                    }
                });
                airDrops.forEach(airDrop1 -> {
                    if (airDrop != airDrop1 && airDrop.isIntersects(airDrop1)) {
                        airDrop.addCollision(airDrop1);
                        airDrop1.addCollision(airDrop);
                    }
                });
            } else {
                hitBoxesToRemove.add(airDrop);
            }
        });
        removeDeadHitboxes(airDrops);

        scraps.forEach(scrap ->
                grounds.forEach(ground -> {
                    if (scrap.isIntersects(ground)) {
                        scrap.addCollision(ground);
                        hitBoxesToRemove.add(scrap);
                    }
                }));
        removeDeadHitboxes(scraps);
        parachutes.forEach(parachute -> {
            if (!parachute.isAlive()) {
                hitBoxesToRemove.add(parachute);
            }
        });
        removeDeadHitboxes(parachutes);


    }

    private void removeDeadHitboxes(List<HitBox> source) {
        if (!hitBoxesToRemove.isEmpty()) {
            source.removeAll(hitBoxesToRemove);
            hitBoxes.removeAll(hitBoxesToRemove);
            hitBoxesToRemove.clear();
        }
    }

    public List<HitBox> getHitBoxes() {
        return hitBoxes;
    }


    public void addHitBox(HitBox hitBox) {
        switch (hitBox.getHitBoxType()) {
            case BULLET:
                bullets.add(hitBox);
                break;
            case ROCKET:
                rockets.add(hitBox);
                break;
            case PLANE_BODY:
                planeBodys.add(hitBox);
                break;
            case PLANE_NOSE:
                planeNoses.add(hitBox);
                break;
            case PLANE_HEAD:
                planeHeads.add(hitBox);
                break;
            case HUMAN:
                humans.add(hitBox);
                break;
            case AIR_DROP:
                airDrops.add(hitBox);
                break;
            case PARACHUTE:
                parachutes.add(hitBox);
                break;
            case GROUND:
            case AMBAR:
                grounds.add(hitBox);
                break;
            case SCRAP_BODY:
                scraps.add(hitBox);
                break;
        }
        this.hitBoxes.add(hitBox);
    }

}
