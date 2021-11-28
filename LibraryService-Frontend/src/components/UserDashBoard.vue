
<template>
  <div id="userAccount">
    <h2>Your Account Information</h2>
        <div> 
            <b-container id="profileInfo"> 
               
                
                <b-row align-h="center" class="mb 3">
                  
                    <b-avatar ></b-avatar>
   
            
                </b-row>
              
                 <b-row align-h="center">  
                   <b-badge href="#" variant="success">Activated</b-badge>
                 </b-row>
               
                <b-row class="my-4">
                    <b-col >
                        <h3>Personal Information</h3>
                        <b-row align-h="center" >
                           <B >{{currentPatron.firstName}}</b>
                           <b>{{currentPatron.lastName}}</b>
                        </b-row>



                         <b-row align-h="center" >
                            <b>Email: </b>
                            <b >{{currentPatron.lastName}}</b>
                           
                        </b-row>
                        <b-row align-h="center" >
                            <b>Addresse: </b>
                            <b >{{currentPatron.lastName}}</b>
                           
                        </b-row>

                         <b-row align-h="center" class="my-4" >
                          <h4>Update your Information</h4>
                          <b>Update Email Adresse:</b>
                          <b-form-input id="updateEmail"  placeholder="sarah@mail.ca"></b-form-input>

                        </b-row >
                        
                        <b-row align-h="center" class="my-1"  >    
                               
                            
                                <b>Change Passoword:</b>
                                <b-form-input id="updatePassword1"  placeholder="New Passowrd"></b-form-input>
                               <b-form-input id="updatePassword1"  placeholder="Comfirm Password"></b-form-input>
                        </b-row>
                        <b-row align-h="center" class="my-2" >
                               <b-button block variant="primary" @click="updateInfo()">Comfirm changes </b-button>
                             
                        </b-row>   
                        
                    </b-col>
                   
                    <b-col>

                        <div class="mybalance">
                            <h3>Current Balance</h3>

                        </div>
                    
                    </b-col>

                    <b-col>

                        <div class="myTranscations">
                            <h3>Transaction History</h3>
                        </div>
                    
                    </b-col>

                 </b-row>
            </b-container>
        </div>
        
        
        
    </div>

 





</template>

<script src="./userInfoViewwing.js">
</script>


<style>



</style>

