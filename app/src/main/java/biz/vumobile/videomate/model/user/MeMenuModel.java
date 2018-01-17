package biz.vumobile.videomate.model.user;

/**
 * Created by IT-10 on 1/16/2018.
 */

public class MeMenuModel {

    private int imageResource;
    private String menuName;

    public MeMenuModel(int imageResource, String menuName) {
        this.imageResource = imageResource;
        this.menuName = menuName;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

}
