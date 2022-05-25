 function upload()
     {
          alert('updating document....');
          var status = true;
          var containsApplet = false;
          var lastDocApplet;
          for ( var i = 0; i < document.applets.length; i++ )
          {
               if ( document.applets[i].getName() == 'DocTransfer' )
               {
                     containsApplet = true;
                     lastDocApplet = document.applets[i];
                     status = lastDocApplet.updateDocuments();
                     if ( status == false )
                     {
                          break;
                     }
               }
          }

          if ( containsApplet == true && status == true )
          {
               lastDocApplet.clientGarbageCollection();
          }
          return status;
     }

