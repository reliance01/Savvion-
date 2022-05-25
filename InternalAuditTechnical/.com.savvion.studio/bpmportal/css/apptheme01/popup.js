// (C) Copyright 2001 Savvion, Inc.  All Rights Reserved.
// This program is an unpublished copyrighted work which is proprietary
//  to Savvion, Inc. and contains confidential information that is not
//  to be reproduced or disclosed to any other person or entity without
//  prior written consent from Savvion, Inc. in each and every instance.
//  -----------------------------------------------------------------

// POPUP TOOLTIPS

var window_x;
var window_y;
var browser_type = 0;
var tip_color = 'ffffaa'; // background color for tooltip

function popup( handle, message ) {

   //browser types: 0, not yet set; 1, NS 6; 2, NS 4+, 3, IE

   if( browser_type == 0 ) prepareTooltips();
   message = '<table bgcolor="#' + tip_color + '"><tr><td>' + message + '</td></tr></table>'; 

   if( browser_type == 2 ) {

      handle = document.layers[ handle ];
   }

   if( browser_type == 1 ) {

      handle = document.getElementById( handle );
   }

   if( browser_type == 2 ) {

      handle.document.open(); 
      handle.document.write( message );
      handle.document.close(); 
      handle.bgcolor= tip_color;
      handle.visibility = 'visible';
      handle.left       = window_x + 5;
      handle.top        = window_y;
   }
   else { 

      if( browser_type == 3 ) {

         handle = document.all[ handle ];
         handle.style.left       = window.event.x + 5;
         handle.style.top        = window.event.y;
      }
      else {

         handle.style.left       = window_x + 5;
         handle.style.top        = window_y;
      }

      handle.innerHTML = message;
      handle.style.visibility = 'visible';
   }
}

function hidePopup( handle ) {

   if( browser_type == 0 ) prepareTooltips();
   if( browser_type < 3 ) {

      if( browser_type == 1 ) {

         handle = document.getElementById( handle );
         handle.style.visibility = 'hidden';
      }
      else {

         handle = document.layers[ handle ];
         handle.visibility = 'hidden';
      }
   }
   else {

      handle = document.all[ 'tooltip' ];
      handle.style.visibility = 'hidden';
   }
}

var tmp = 0;
function updateCoords( e )
{
   if( browser_type == 1 ) {

      window_x = e.clientX; 
      window_y = e.clientY;
   }

   if( browser_type == 2 ) { 

      window_x = e.pageX; 
      window_y = e.pageY;
   }

   routeEvent( e );
}

function prepareTooltips() {

   if( document.getElementById ) { // ns6

      browser_type = 1;
   }
   else if( document.layers ) { // ns4+

      browser_type = 2;
   }
   if( document.all ) { // IE 

      browser_type = 3;
   }

   if ( browser_type < 3 ) { // netscape event handling

      document.captureEvents(Event.MOUSEMOVE);
      document.onmousemove = updateCoords;
   }
}

if( browser_type == 0 ) prepareTooltips(); // initialize
