function getElementById_s(n){var t=null;return document.getElementById?t=get_Element(n):document.all&&(t=document.all[n]),t}function get_Element(n){return document.getElementById(n)||document.getElementsByName(n).item(0)}function closeAddressValidatorModal(){jQuery(".close").trigger("click")}function addressValidatorContinue(){useAddressValidator="";doAddAddress(document.checkoutform);closeAddressValidatorModal()}function verify_address(n,t,i,r,u,f,e,o){if(useAddressValidator=="1"){get_Element("hdnAddrressValidatorResult").value="";var h="addressvalidator.asp?no-cache="+Math.random(),s="doaction=verify&ct="+o;if(s+="&company="+encodeURIComponent(e),s+="&address="+encodeURIComponent(n),s+="&address2="+encodeURIComponent(t),s+="&city="+encodeURIComponent(i),s+="&state="+encodeURIComponent(r),s+="&zip="+encodeURIComponent(f),n!="")return lastShippingAddress=s,jQuery.ajax({url:h,dataType:"html",type:"POST",data:s,cache:!1,success:function(n){jQuery("#divAddrressValidator").html(n)},error:reportError}),!1}}function selectAddress(n,t,i,r,u,f,e,o,s,h){closeAddressValidatorModal();var f=jQuery("#shipping_address").val(),e=jQuery("#shipping_address2").val(),o=jQuery("#shipping_city").val(),s=jQuery("#shipping_state").val(),h=jQuery("#shipping_zip").val();f=n;e=t;o=i;h=u;s=r}function reportError(n){ajax_request_progress=0;n.status>0&&alert("Error processing request "+n.status+" - "+n)}function doAddAddress(n){var t;if(n=document.checkoutform,t="",document.checkoutform.address_alias.value.trim()==""&&(t+=" Please enter a address alias. Example: My address \n"),document.checkoutform.shipping_firstname.value.trim()==""&&(t+=" Please enter your First Name \n"),document.checkoutform.shipping_lastname.value.trim()==""&&(t+=" Please enter your Last Name \n"),document.checkoutform.shipping_address.value.trim()==""&&(t+=" Please enter your address \n"),document.checkoutform.shipping_city.value.trim()==""&&(t+=" Please enter your City \n"),document.checkoutform.shipping_country.value.trim()==""&&(t+=" Please enter your Country \n"),document.checkoutform.shipping_state.value.trim()==""&&(t+=" Please enter your Shipping State \n"),document.checkoutform.shipping_zip.value.trim()==""&&(t+=" Please enter your zip code \n"),t!="")return alert(t),!1;var r=jQuery("#shipping_company").val(),u=jQuery("#shipping_address").val(),f=jQuery("#shipping_address2").val(),e=jQuery("#shipping_city").val(),o=jQuery("#shipping_state").val(),i=jQuery("#shipping_country").val(),s=jQuery("#shipping_zip").val();useAddressValidator=="1"&&i=="US"?verify_address(u,f,e,o,i,s,r,"addressbook"):n.submit()}var useAddressValidator