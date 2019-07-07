package classes.Multiplayer;

import classes.panes.AdvancedPane;
import classes.Multiplayer.pockets.*;
import classes.Multiplayer.onlineControllers.*;
import classes.effects.LeftFaceEffect;
import classes.effects.RightFaceEffect;
import classes.planes.Plane;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PocketManager extends AdvancedPane {
    private Map<Integer, OnlinePocket> incomingOnlinePockets = new LinkedHashMap<>();
    private Map<Integer, OnlinePocket> pocketsToCheck = new LinkedHashMap<>();
    private Map<Integer, OnlineController> onlineControllers = new LinkedHashMap<>();
    private List<Integer> controllersIdToRemove = new ArrayList<>();
    private List<Integer> deadIds = new ArrayList<>();
    public static Plane plane;
    public static Plane enemyPlane;

    void update() {
        onlineControllers.forEach((id, shell) -> {
            if (shell.isAlive()) {
                shell.applySettings();
            }
        });

    }

    void updatePockets() {
        pocketsToCheck.putAll(incomingOnlinePockets);
        pocketsToCheck.forEach((id, pocket) -> {
            if (pocket.isNew()) {
                pocket.setNew(false);
                if (pocket instanceof HumanPocket) {
                    HumanController controller = new HumanController(pocket.getX(), pocket.getY(), ((HumanPocket) pocket).getHumanSide());
                    onlineControllers.put(id, controller);
                    panesToAdd.add(controller.getChild());
                } else if (pocket instanceof RocketPocket) {
                    if (!deadIds.contains(pocket.getPocketId())) {
                        RocketController controller = new RocketController(pocket.getX(), pocket.getY(), pocket.getRotation());
                        onlineControllers.put(id, controller);
                        panesToAdd.add(controller.getChild());
                    }


                } else if (pocket instanceof PlanePocket) {
                    if (!deadIds.contains(pocket.getPocketId())) {
                        PlaneController controller = new PlaneController(pocket.getX(), pocket.getY(), pocket.getRotation(), ((PlanePocket) pocket).getPlaneSide());
                        onlineControllers.put(id, controller);
                        panesToAdd.add(controller.getChild());
                        enemyPlane = (Plane) controller.getChild();
                    }


                }else if (pocket instanceof ScrapPocket){
                    ScrapController controller = new ScrapController(pocket.getX(),pocket.getY(),((ScrapPocket) pocket).getScrapSide());
                    onlineControllers.put(pocket.getPocketId(),controller);
                    panesToAdd.add(controller.getChild());
                }
                else if (pocket instanceof BulletPocket) {
                    BulletController controller = new BulletController(pocket.getX(), pocket.getY());
                    onlineControllers.put(pocket.getPocketId(), controller);
                    panesToAdd.add(controller.getChild());
                }
                else if (pocket instanceof AirDropPocket) {
                    AirDropController controller = new AirDropController(pocket.getX(), pocket.getY(), ((AirDropPocket) pocket).getBonusNumber());
                    onlineControllers.put(pocket.getPocketId(), controller);
                    panesToAdd.add(controller.getChild());
                }
                else if (pocket instanceof AirDropAnswerPocket) {
                    try {
                        pocketsTosend.get(((AirDropAnswerPocket)pocket).getAnswerId()).getOnlinebleParent().finish();
                    } catch (NullPointerException e) {

                    }

                }
                else if (pocket instanceof PointsPocket) {
                    setPointsData(((PointsPocket) pocket).getLeftPoints(), ((PointsPocket) pocket).getRightPoints());
                }
                else if (pocket instanceof HeadShotPocket) {
                    plane.addHeadShot();
                }
                else if (pocket instanceof FaceEffectPocket) {
                    FaceEffectPocket pocket1 = (FaceEffectPocket) pocket;
                    switch (pocket1.getFaceSide()) {
                        case 0:
                            effects.add(new LeftFaceEffect(pocket1.getTextNumber()));
                            break;
                        case 1:
                            effects.add(new RightFaceEffect(pocket1.getTextNumber()));
                            break;
                    }
                } else if (pocket instanceof PlaneAnswerPocket) {
                    PlaneAnswerPocket answerPocket = (PlaneAnswerPocket) pocket;
                    plane.setHealthFromController(answerPocket.getHealth());
                    plane.setArmorLeft(answerPocket.getArmor());


                } else if (pocket instanceof HumanAnswerPocket) {
                    HumanAnswerPocket answerPocket = (HumanAnswerPocket) pocket;
                    plane.getHuman().dropParachuteFromShell();
                    if (!answerPocket.isHumanAlive()) {
                        plane.getHuman().startDeadAnimation();
                    }
                } else if (pocket instanceof RocketAnswerPocket) {
                    RocketAnswerPocket answerPocket = (RocketAnswerPocket) pocket;
                    try {
                        pocketsTosend.get(answerPocket.getAnswerId()).getOnlinebleParent().finish();
                    } catch (NullPointerException e) {

                    }

                }


            }

        });


        onlineControllers.forEach((id, controller) -> {
            if (controller.isAlive() && incomingOnlinePockets.containsKey(id)) {
                controller.setPocket(incomingOnlinePockets.get(id));
            } else {
                controllersIdToRemove.add(id);
                deadIds.add(id);
                controller.finish();
            }
        });
        controllersIdToRemove.forEach(id -> onlineControllers.remove(id));
        controllersIdToRemove.clear();

    }


    void removeOldPockets() {
        List<Integer> pocketsToDelete = new ArrayList<>();
        pocketsToCheck.forEach((id, pocket) -> {
            if (!onlineControllers.containsKey(id)) {
                pocketsToDelete.add(id);

            }
        });
        pocketsToDelete.forEach(id -> pocketsToCheck.remove(id));

    }

    void addNewPockets(List<OnlinePocket> pockets) {
        pockets.forEach(pocket -> {
            if (pocketsToCheck.containsKey(pocket.getPocketId())) {
                pocket.setNew(false);
            }
            incomingOnlinePockets.put(pocket.getPocketId(), pocket);
        });
    }

    void clearIncomingPockets() {
        incomingOnlinePockets.clear();
    }

    public Map<Integer, OnlinePocket> getIncomingOnlinePockets() {
        return incomingOnlinePockets;
    }


}
