package nomox.engine3d;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {
	//public static final int VK_NONE            = 0;
	public static final int VK_ESCAPE          = GLFW_KEY_ESCAPE;
	public static final int VK_1               = GLFW_KEY_1;
	public static final int VK_2               = GLFW_KEY_2;
	public static final int VK_3               = GLFW_KEY_3;
	public static final int VK_4               = GLFW_KEY_4;
	public static final int VK_5               = GLFW_KEY_5;
	public static final int VK_6               = GLFW_KEY_6;
	public static final int VK_7               = GLFW_KEY_7;
	public static final int VK_8               = GLFW_KEY_8;
	public static final int VK_9               = GLFW_KEY_9;
	public static final int VK_0               = GLFW_KEY_0;
	public static final int VK_MINUS           = GLFW_KEY_MINUS; /* - on main keyboard */
	public static final int VK_EQUALS          = GLFW_KEY_EQUAL;
	public static final int VK_ACKSPACE        = GLFW_KEY_BACKSPACE; /* backspace */
	public static final int VK_TAB             = GLFW_KEY_TAB;
	public static final int VK_Q               = GLFW_KEY_Q;
	public static final int VK_W               = GLFW_KEY_W;
	public static final int VK_E               = GLFW_KEY_E;
	public static final int VK_R               = GLFW_KEY_R;
	public static final int VK_T               = GLFW_KEY_T;
	public static final int VK_Y               = GLFW_KEY_Y;
	public static final int VK_U               = GLFW_KEY_U;
	public static final int VK_I               = GLFW_KEY_I;
	public static final int VK_O               = GLFW_KEY_O;
	public static final int VK_P               = GLFW_KEY_P;
	public static final int VK_LBRACKET        = GLFW_KEY_LEFT_BRACKET;
	public static final int VK_RBRACKET        = GLFW_KEY_RIGHT_BRACKET;
	public static final int VK_RETURN          = GLFW_KEY_ENTER; /* Enter on main keyboard */
	public static final int VK_LCONTROL        = GLFW_KEY_LEFT_CONTROL;
	public static final int VK_A               = GLFW_KEY_A;
	public static final int VK_S               = GLFW_KEY_S;
	public static final int VK_D               = GLFW_KEY_D;
	public static final int VK_F               = GLFW_KEY_F;
	public static final int VK_G               = GLFW_KEY_G;
	public static final int VK_H               = GLFW_KEY_H;
	public static final int VK_J               = GLFW_KEY_J;
	public static final int VK_K               = GLFW_KEY_K;
	public static final int VK_L               = GLFW_KEY_L;
	public static final int VK_SEMICOLON       = GLFW_KEY_SEMICOLON;
	public static final int VK_APOSTROPHE      = GLFW_KEY_APOSTROPHE;
	public static final int VK_GRAVE           = GLFW_KEY_GRAVE_ACCENT; /* accent grave */
	public static final int VK_LSHIFT          = GLFW_KEY_LEFT_SHIFT;
	public static final int VK_BACKSLASH       = GLFW_KEY_BACKSLASH;
	public static final int VK_Z               = GLFW_KEY_Z;
	public static final int VK_X               = GLFW_KEY_X;
	public static final int VK_C               = GLFW_KEY_C;
	public static final int VK_V               = GLFW_KEY_V;
	public static final int VK_B               = GLFW_KEY_B;
	public static final int VK_N               = GLFW_KEY_N;
	public static final int VK_M               = GLFW_KEY_M;
	public static final int VK_COMMA           = GLFW_KEY_COMMA;
	public static final int VK_PERIOD          = GLFW_KEY_PERIOD; /* . on main keyboard */
	public static final int VK_SLASH           = GLFW_KEY_SLASH; /* / on main keyboard */
	public static final int VK_RSHIFT          = GLFW_KEY_RIGHT_SHIFT;
	public static final int VK_MULTIPLY        = GLFW_KEY_KP_MULTIPLY; /* * on numeric keypad */
	public static final int VK_LALT            = GLFW_KEY_LEFT_ALT; /* left Alt */
	public static final int VK_SPACE           = GLFW_KEY_SPACE;
	public static final int VK_F1              = GLFW_KEY_F1;
	public static final int VK_F2              = GLFW_KEY_F2;
	public static final int VK_F3              = GLFW_KEY_F3;
	public static final int VK_F4              = GLFW_KEY_F4;
	public static final int VK_F5              = GLFW_KEY_F5;
	public static final int VK_F6              = GLFW_KEY_F6;
	public static final int VK_F7              = GLFW_KEY_F7;
	public static final int VK_F8              = GLFW_KEY_F8;
	public static final int VK_F9              = GLFW_KEY_F9;
	public static final int VK_F10             = GLFW_KEY_F10;
	public static final int VK_NUMLOCK         = GLFW_KEY_NUM_LOCK;
	public static final int VK_SCROLL          = GLFW_KEY_SCROLL_LOCK; /* Scroll Lock */
	public static final int VK_NUMPAD7         = GLFW_KEY_KP_7;
	public static final int VK_NUMPAD8         = GLFW_KEY_KP_8;
	public static final int VK_NUMPAD9         = GLFW_KEY_KP_9;
	public static final int VK_SUBTRACT        = GLFW_KEY_KP_SUBTRACT; /* - on numeric keypad */
	public static final int VK_NUMPAD4         = GLFW_KEY_KP_4;
	public static final int VK_NUMPAD5         = GLFW_KEY_KP_5;
	public static final int VK_NUMPAD6         = GLFW_KEY_KP_6;
	public static final int VK_ADD             = GLFW_KEY_KP_ADD; /* + on numeric keypad */
	public static final int VK_NUMPAD1         = GLFW_KEY_KP_1;
	public static final int VK_NUMPAD2         = GLFW_KEY_KP_2;
	public static final int VK_NUMPAD3         = GLFW_KEY_KP_3;
	public static final int VK_NUMPAD0         = GLFW_KEY_KP_0;
	public static final int VK_DECIMAL         = GLFW_KEY_KP_DECIMAL; /* . on numeric keypad */
	public static final int VK_F11             = GLFW_KEY_F11;
	public static final int VK_F12             = GLFW_KEY_F12;
	public static final int VK_NUMPADENTER     = GLFW_KEY_KP_ENTER; /* Enter on numeric keypad */
	public static final int VK_RCONTROL        = GLFW_KEY_RIGHT_CONTROL;
	public static final int VK_DIVIDE          = GLFW_KEY_KP_DIVIDE; /* / on numeric keypad */
	public static final int VK_RALT            = GLFW_KEY_RIGHT_ALT; /* right Alt */
	public static final int VK_PAUSE           = GLFW_KEY_PAUSE; /* Pause */
	public static final int VK_HOME            = GLFW_KEY_HOME; /* Home on arrow keypad */
	public static final int VK_UP              = GLFW_KEY_UP; /* UpArrow on arrow keypad */
	public static final int VK_PAGE_UP         = GLFW_KEY_PAGE_UP; /* PgUp on arrow keypad */
	public static final int VK_PAGE_DOWN       = GLFW_KEY_PAGE_DOWN; /* PgDown on arrow keypad */
	public static final int VK_LEFT            = GLFW_KEY_LEFT; /* LeftArrow on arrow keypad */
	public static final int VK_RIGHT           = GLFW_KEY_RIGHT; /* RightArrow on arrow keypad */
	public static final int VK_END             = GLFW_KEY_END; /* End on arrow keypad */
	public static final int VK_DOWN            = GLFW_KEY_DOWN; /* DownArrow on arrow keypad */
	public static final int VK_INSERT          = GLFW_KEY_INSERT; /* Insert on arrow keypad */
	public static final int VK_DELETE          = GLFW_KEY_DELETE; /* Delete on arrow keypad */
	
	protected static List<Integer> pressed_keys = new ArrayList<>();
	
	public static boolean isKeyDown(int key) {
		return pressed_keys.contains(key);
	}
}