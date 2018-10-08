package linhtruong.com.commons;

/**
 * App constant value
 *
 * @author linhtruong
 * @date 10/3/18 - 10:51.
 * @organization VED
 */
public interface MSConstValue {
    interface SIGN {
        String DEBUG = "ucP4AvraiM6NKfRWDn5i8Qt-b2w=";
        String RELEASE = "UxDqjuzzNpjNWxFpof_jHlyVpT4=";
    }

    interface EXTRA {
        String EXTRA_CATEGORY_CONFIG = "EXTRA_CATEGORY_CONFIG";
    }

    interface SPAN {
        int TIMELINE_CATEGORY_SPAN_SIZE = 2;
    }

    interface TIMELINE_STATUS {
        String SOLD_OUT = "sold_out";
    }

    interface THEME {
        int LIGHT = 0;
        int DARK = 1;
    }

    interface GALLERY {
        String DIRECTORY_NAME = "mershop";
        String IMAGE_EXTENSION = "jpg";
        int MEDIA_TYPE_IMAGE = 1;
    }

    interface BITMAP {
        int SAMPLE_SIZE = 8;
    }

    interface REQUEST_CODE {
        int CAMERA_CAPTURE_IMAGE = 100;
    }
}
