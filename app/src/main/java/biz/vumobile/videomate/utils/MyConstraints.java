package biz.vumobile.videomate.utils;

/**
 * Created by IT-10 on 1/10/2018.
 */

public class MyConstraints {
    public static final String VIDEO_PATH = "video_path";
    public static final String AUDIO_PATH = "audio_path";
    public static final String FILE_PATH = "/storage/emulated/0/VideoMate/";
    public static final String FILE_PATH_SD_CARD = "/mnt/sdcard/VideoMate/";
    public static final long VIDEO_RECORD_TIME = 15000;
    public static final String VIDEO_POSITION = "position";

    public static final String FRAGMENT_TAG_ME = "FragmentMe";

    public static final String API_BASE = "http://wap.shabox.mobi/VUMate/";
    public static final String GET_ALL_POSTS_FOLLOW = "api/post/GetPostFollowed"; // http://wap.shabox.mobi/VUMate/api/post/GetPostFollowed?id=38
    public static final String GET_ALL_POSTS_LATEST = "api/post/GetPostLatest"; // http://wap.shabox.mobi/VUMate/api/post/GetPostLatest?id=38
    public static final String GET_ALL_POSTS_POPULAR = "api/post/GetPostPopular"; // http://wap.shabox.mobi/VUMate/api/post/GetPostPopular?id=38
    public static final String API_POST_DATA = "api/post/Postup";
    public static final String API_GIVE_LIKE = "api/Post/PostLike";
    public static final String POST_COMMENT = "api/Post/PostComment";
    public static final String GET_COMMENT = "api/post/GetComment";
    public static final String POST_VIEW = "api/Post/PostView";
    public static final String POST_FOLLOWER_USER = "api/User/PostFollowUser";
    public static final String GET_FOLLOWERS = "api/User/GetFollowerList";

    public static final String API_REGISTER_USER = "api/User/PostNewUser";
    public static final String API_GET_USER = "api/User/GetUserInfo"; //?id=5
    public static final String API_POST_UPDATE = "api/User/PostUpdateUser";

    // Shared pref
    public static final String SHAR_PREF_NAME_USER = "pref_name_user";
    public static final String SHAR_PREF_USER_ID = "user_id";

    // Audio list
    public static final String API_GET_AUDIO_CAT = "api/post/GetCategory";
    public static final String API_GET_AUDIO = "api/post/GetSongListByCategory"; //?id=2


}
