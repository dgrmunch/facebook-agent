package com.xmunch.facebook.aux;

public enum GlobalValues {

	FACEBOOK_REDIRECT {
		@Override
		public String get() {
			return "redirect:/connect/facebook";
		}
	},
	JSON_ATTRIBUTE {
		@Override
		public String get() {
			return "json";
		}
	},
	DONE_TEMPLATE {
		@Override
		public String get() {
			return "done";
		}
	};

	public abstract String get();
}
