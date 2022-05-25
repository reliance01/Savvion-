// ** I18N

// Calendar EN language
// Author: Mihai Bazon, <mihai_bazon@yahoo.com>
// Encoding: any
// Distributed under the same terms as the calendar itself.

// For translators: please use UTF-8 if possible.  We strongly believe that
// Unicode is the answer to a real internationalized world.  Also please
// include your contact information in the header, as can be seen above.

// full day names
Calendar._DN = new Array
("\u65e5\u66dc\u65e5",
 "\u6708\u66dc\u65e5",
 "\u706b\u66dc\u65e5",
 "\u6c34\u66dc\u65e5",
 "\u6728\u66dc\u65e5",
 "\u91d1\u66dc\u65e5",
 "\u571f\u66dc\u65e5",
 "\u65e5\u66dc\u65e5");

// Please note that the following array of short day names (and the same goes
// for short month names, _SMN) isn't absolutely necessary.  We give it here
// for exemplification on how one can customize the short day names, but if
// they are simply the first N letters of the full name you can simply say:
//
//   Calendar._SDN_len = N; // short day name length
//   Calendar._SMN_len = N; // short month name length
//
// If N = 3 then this is not needed either since we assume a value of 3 if not
// present, to be compatible with translation files that were written before
// this feature.

// short day names
Calendar._SDN = new Array
("\u65e5",
 "\u6708",
 "\u706b",
 "\u6c34",
 "\u6728",
 "\u91d1",
 "\u571f",
 "\u65e5");

// First day of the week. "0" means display Sunday first, "1" means display
// Monday first, etc.
Calendar._FD = 0;

// full month names
Calendar._MN = new Array
("1\u6708",
 "2\u6708",
 "3\u6708",
 "4\u6708",
 "5\u6708",
 "6\u6708",
 "7\u6708",
 "8\u6708",
 "9\u6708",
 "10\u6708",
 "11\u6708",
 "12\u6708");

// short month names
Calendar._SMN = new Array
("1\u6708",
 "2\u6708",
 "3\u6708",
 "4\u6708",
 "5\u6708",
 "6\u6708",
 "7\u6708",
 "8\u6708",
 "9\u6708",
 "10\u6708",
 "11\u6708",
 "12\u6708");

// tooltips
Calendar._TT = {};
Calendar._TT["INFO"] = "\u30ab\u30ec\u30f3\u30c0\u30fc\u306b\u3064\u3044\u3066";

Calendar._TT["ABOUT"] =
"DHTML Date/Time Selector\n" +
"(c) dynarch.com 2002-2005 / Author: Mihai Bazon\n" + // don't translate this this ;-)
"\u6700\u65b0\u30d0\u30fc\u30b8\u30e7\u30f3\u306b\u3064\u3044\u3066\u306f\u3053\u3061\u3089\u3092\u53c2\u7167\u3057\u3066\u304f\u3060\u3055\u3044\u3002 http://www.dynarch.com/projects/calendar/\n" +
"GNU LGPL \u3067\u3068\u914d\u5e03\u3002\u8a73\u7d30\u306b\u3064\u3044\u3066\u306f\u3053\u3061\u3089\u3092\u53c2\u7167\u3057\u3066\u304f\u3060\u3055\u3044\u3002 http://gnu.org/licenses/lgpl.html " +
"\n\n" +
"\u65e5\u4ed8\u306e\u9078\u629e:\n" +
"- \xab \u30dc\u30bf\u30f3\u3001\xbb \u30dc\u30bf\u30f3\u3092\u4f7f\u7528\u3057\u3066\u5e74\u3092\u9078\u629e\u3057\u307e\u3059\u3002\n" +
"- " + String.fromCharCode(0x2039) + " \u30dc\u30bf\u30f3\u3001" + String.fromCharCode(0x203a) + " \u30dc\u30bf\u30f3\u3092\u4f7f\u7528\u3057\u3066\u6708\u3092\u9078\u629e\u3057\u307e\u3059\u3002\n" +
"- \u3088\u308a\u65e9\u304f\u9078\u629e\u3059\u308b\u306b\u306f\u4e0a\u8a18\u306e\u30dc\u30bf\u30f3\u4e0a\u3092\u9577\u62bc\u3057\u3057\u307e\u3059\u3002";
Calendar._TT["ABOUT_TIME"] = "\n\n" +
"\u6642\u9593\u306e\u9078\u629e:\n" +
"- \u9032\u3081\u308b\u306b\u306f\u6642\u9593\u30d1\u30fc\u30c4\u306e\u3044\u305a\u308c\u304b\u3092\u30af\u30ea\u30c3\u30af\u3057\u307e\u3059\n" +
"- \u9045\u3089\u305b\u308b\u306b\u306f Shift \u30ad\u30fc\u3092\u62bc\u3057\u306a\u304c\u3089\u30af\u30ea\u30c3\u30af\u3057\u307e\u3059\n" +
"- \u3088\u308a\u65e9\u304f\u9078\u629e\u3059\u308b\u306b\u306f\u30af\u30ea\u30c3\u30af\u3057\u3066\u30c9\u30e9\u30c3\u30b0\u3057\u307e\u3059\u3002";

Calendar._TT["PREV_YEAR"] = "\u524d\u5e74 (\u9577\u62bc\u3057\u3067\u30e1\u30cb\u30e5\u30fc\u8868\u793a)";
Calendar._TT["PREV_MONTH"] = "\u524d\u6708 (\u9577\u62bc\u3057\u3067\u30e1\u30cb\u30e5\u30fc\u8868\u793a)";
Calendar._TT["GO_TODAY"] = "\u4eca\u65e5";
Calendar._TT["NEXT_MONTH"] = "\u7fcc\u6708 (\u9577\u62bc\u3057\u3067\u30e1\u30cb\u30e5\u30fc\u8868\u793a)";
Calendar._TT["NEXT_YEAR"] = "\u7fcc\u5e74 (\u9577\u62bc\u3057\u3067\u30e1\u30cb\u30e5\u30fc\u8868\u793a)";
Calendar._TT["SEL_DATE"] = "\u65e5\u4ed8\u9078\u629e";
Calendar._TT["DRAG_TO_MOVE"] = "\u30c9\u30e9\u30c3\u30b0\u3057\u3066\u79fb\u52d5";
Calendar._TT["PART_TODAY"] = " (\u4eca\u65e5)";

// the following is to inform that "%s" is to be the first day of week
// %s will be replaced with the day name.
Calendar._TT["DAY_FIRST"] = "%s \u3092\u6700\u521d\u306b\u8868\u793a";

// This may be locale-dependent.  It specifies the week-end days, as an array
// of comma-separated numbers.  The numbers are from 0 to 6: 0 means Sunday, 1
// means Monday, etc.
Calendar._TT["WEEKEND"] = "0,6";

Calendar._TT["CLOSE"] = "\u9589\u3058\u308b";
Calendar._TT["TODAY"] = "\u4eca\u65e5";
Calendar._TT["TIME_PART"] = "Shift \u30ad\u30fc\u3092\u62bc\u3057\u306a\u304c\u3089\u30af\u30ea\u30c3\u30af\u307e\u305f\u306f\u30c9\u30e9\u30c3\u30b0\u3057\u3066\u5024\u3092\u5909\u66f4";

// date formats
Calendar._TT["DEF_DATE_FORMAT"] = "%Y-%m-%d";
Calendar._TT["TT_DATE_FORMAT"] = "%a, %b %e";

Calendar._TT["WK"] = "\u9031";
Calendar._TT["TIME"] = "\u6642\u9593:";
