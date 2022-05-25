/*< number numbers *******************************************************************/
fValidate.prototype.number = function( type, lb, ub )
{
     if(this.elem != undefined && this.elem.bok == true){
        if(this.elem.value == ''){
            this.elem.value = 0;
            this.elem.validated == true;
            return;
        }
     }
     var elementValue = this.elem.value;
     
    if ( this.typeMismatch( 'text' ) ) return;
    var num  = ( type == 0 ) ? parseInt( elementValue, 10 ) : parseFloat( elementValue);
    lb       = this.setArg( lb, 0 );
    ub       = this.setArg( ub, Number.infinity );
  
    // added by keivan
    lb = ( type == 0 ) ? parseInt( lb ) : parseFloat( lb );
    ub = ( type == 0 ) ? parseInt( ub ) : parseFloat( ub ); 
    // end of addition
    
    if ( lb > ub )
    {
        this.devError( [lb, ub, this.elem.name] );
        return;
    }
    
    var fail = Boolean( isNaN( num ) || num != elementValue );
    if(type==0 && !fail && this.elem != undefined) 
        fail = Boolean(elementValue.indexOf(".") != -1 );    
    if ( !fail )
    {
        switch( true )
        {
            case ( lb != false && ub != false && !isNaN(ub)) : fail = !Boolean( lb <= num && num <= ub ); break;
            case ( lb != false ) : fail = Boolean( num < lb ); break;
            case ( ub != false && !isNaN(ub)) : fail = Boolean( num > ub ); break;
        }
    }
    if ( fail )
    {
        this.skip = false;
        if(typeof this.elem.label == 'undefined')
        {
                 var fieldName = this.elem.fName;
             if(fieldName.indexOf("bizsite dataslot") != -1)
             {
                 if(ub != undefined && !isNaN(ub)){ 
                    this.throwError( [fieldName.substr(16,fieldName.length), lb, ub] );
                 }else{
                    this.throwError( [fieldName.substr(16,fieldName.length)],1 );
                }
                
             }
             else {
                 if(ub != undefined && !isNaN(ub)){
                    this.throwError( [fieldName, lb, ub ] );   
                 }else{
                    this.throwError( [fieldName] ,1 );   
                 }
            }
        }
        else
        {
            var fieldLabel = sbm.filterLabel(this.elem.label.innerHTML);
            if(ub != undefined && !isNaN(ub)){
                this.throwError([fieldLabel, lb, ub]);
            }else{
                this.throwError([fieldLabel],1);
            }
        }
        return;
    }
    this.elemPass = true;
}
/*/>*/

/*< decimal numbers *******************************************************************/
fValidate.prototype.decimal = function( lval, rval )
{
    if ( this.typeMismatch( 'text' ) ) return;
    var regex = '', elem = this.elem;
    
    //added by keivan
    if(lval == 'undefined' || (lval.length == 0)) lval = '*';
    if(rval == 'undefined' || (rval.length == 0)) rval = '*';
    // end of addition
    
    if ( lval != '*' ) lval = parseInt( lval, 10 );
    if ( rval != '*' ) rval = parseInt( rval, 10 );
    
    if(elem.value == -0.0)
        regex = "^[0-9]*\\.[0-9]+$";
    else if ( lval == 0 )
        regex = "^-?\\.[0-9]{1,"+ rval +"}$"; // Fix for CR#RPM00025533 
    else if ( lval == '*'  && rval == '*')
        regex = "^-?[0-9]*\\.[0-9]+$";
    else if ( lval == '*' && rval != '*')
        regex = "^-?[0-9]*(\\.[0-9]{1,"+ rval +"})?$"; // Fix for CR#RPM00025533
    else if ( rval == '*' && lval != '*' )
        regex = "^-?[0-9]{" + lval + "}\\.[0-9]+$";
    else
		regex = "^-?[0-9]{" + lval + "}(\\.[0-9]{1,"+ rval +"})?$"; // Fix for CR#RPM00025533
        
    regex = new RegExp( regex );

    if ( !regex.test( elem.value ) )
    {
        //this.throwError( [elem.value,elem.fName] );
        if(typeof this.elem.label == 'undefined')
        {
            var fieldName = this.elem.fName;
            if(fieldName.indexOf("bizsite dataslot") != -1)
            {
               this.throwError( [elem.value,fieldName.substr(16,fieldName.length),lval,rval] );
            }
            else 
                this.throwError( [elem.value,fieldName,lval,rval] );
        }
        else
        {
            var fieldLabel = sbm.filterLabel(this.elem.label.innerHTML);
            this.throwError( [elem.value,fieldLabel,lval,rval] );
        }
    }   
}
/*/>*/
