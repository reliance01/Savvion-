var droolsKeywords = new Array("when", "then", "rule", "end", "update",
                               "modify", "retract", "insert", "insertLogical", "salience", 
                               "import", "expander", "package", "function", "global", 
                               "query", "exists", "eval", "agenda-group", "lock-on-active", 
                               "no-loop", "duration", "not", "auto-focus", "activation-group", 
                               "new", "contains", "matches", "excludes", "template", 
                               "from", "accumulate", "collect", "date-effective", "date-expires", 
                               "enabled", "forall", "dialect", "ruleflow-group", "modifyRetract", 
                               "modifyInsert", "memberOf", "and", "or", "extends");

var literalRegExStr = "\"(?:(?:\\\\.)|(?:[^\"\\\\]))*\"";
var singleLineCommentRegExStr =  "(?://.*|#.*)";
var multilLineCommentRegExStr = "(?:/\\*(?:.|[\\n\\r])*?\\*/)";
var keywordsRegExStr = "([^0-9A-Za-z_$]|^)(" + mergeKeywords() + ")([^0-9A-Za-z_$]|$)";
var regExFlags = "img";

function mergeKeywords() {
    var mergedKeywordsStr = "";
    for(var i=0;i<droolsKeywords.length;i++) {
        if(i != 0)
              mergedKeywordsStr += "|";
                
        mergedKeywordsStr += droolsKeywords[i];
    }
    return mergedKeywordsStr;
}
 
function HighlightDRLSyntax(inputText) {
    var text = inputText;
    var returnStr = "";
    var previousOffset = 0;
    
    var combinedRegEx = new RegExp("((?:" + literalRegExStr +")|(?:" + singleLineCommentRegExStr +")|(?:" + multilLineCommentRegExStr +"))", regExFlags);
    text = text.replace(combinedRegEx, function(str, p1, offset, s){
        if(previousOffset < offset){
            returnStr += markKeywords(s.substring(previousOffset, offset));
        }
        previousOffset = offset + str.length;
        
        if(str.search(new RegExp(literalRegExStr, regExFlags)) == 0){
            returnStr += "<span class=\"literal\"> " + str + "</span>";
        } else {
            returnStr += "<span class=\"comment\"> " + str + "</span>";
        }
    });

    if(previousOffset < inputText.length){
        returnStr += markKeywords(inputText.substring(previousOffset, inputText.length));
    }
    
    return "<pre><div class=\"drlsyntax\">" + returnStr + "</div></pre>";
}

function markKeywords(inputText) {
    var text = inputText;
    return text.replace(new RegExp(keywordsRegExStr, regExFlags),"$1<span class=\"keyword\">$2</span>$3");
}
 
function applyKeywords(parentElementId) {
    var parentEle = document.getElementById(parentElementId);
    parentEle.innerHTML = HighlightDRLSyntax(parentEle.innerHTML);
}
