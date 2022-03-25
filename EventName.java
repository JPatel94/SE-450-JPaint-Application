package view;

public enum EventName {
	DRAW{
		@Override
		public String toString() {
			return "CONFIGURE SHAPE";
		}
	},
	UNDO {
		@Override
		public String toString() {
			return "UNDO";
		}
	},
	REDO {
		@Override
		public String toString() {
			return "REDO";
		}
	},
	MOVE {
		@Override
		public String toString() {
			return "MOVE";
		}
	},
	SELECT {
		@Override
		public String toString() {
			return "SELECT";
		}
	},
	COPY {
		@Override
		public String toString() {
			return "COPY";
		}
	},
	PASTE {
		@Override
		public String toString() {
			return "PASTE";
		}
	},
	DELETE {
		@Override
		public String toString() {
			return "DELETE";
		}
	},
	GROUP {
		@Override
		public String toString() {
			return "GROUP";
		}
	},
	UNGROUP {
		@Override
		public String toString() {
			return "UNGROUP";
		}
	},

}
/*
public enum EventName {
	CHOOSE_SHAPE{
		@Override
		public String toString() {
			return "CHOOSE SHAPE";
		}
	},
	CHOOSE_PRIMARY_COLOR {
		@Override
		public String toString() {
			return "CHOOSE PRIMARY COLOR";
		}
	},
	CHOOSE_SECONDARY_COLOR {
		@Override
		public String toString() {
			return "CHOOSE SECONDARY COLOR";
		}
	},
	CHOOSE_SHADING_TYPE {
		@Override
		public String toString() {
			return "CHOOSE SHADING TYPE";
		}
	},
	CHOOSE_MOUSE_MODE {
		@Override
		public String toString() {
			return "CHOOSE MOUSE MODE";
		}
	},
	UNDO,
	REDO,
	COPY,
	PASTE,
	DELETE,
	GROUP,
	UNGROUP
}
*/
