
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
                        <b-row align-h="center" class="d-inline" >
                           <b class="d-inline" >{{currentPatron.firstName}} </b>

                           <b class="d-inline" > {{currentPatron.lastName}}</b>
                        </b-row>



                         <b-row align-h="center" >
                            <b>Email: </b>
                            <b >{{currentPatron.lastName}}</b>
                           
                        </b-row>
                        <b-row align-h="center" >
                            <b>Address: </b>
                            <b >{{currentPatron.lastName}}</b>
                           
                        </b-row>

                         <b-row align-h="center" class="my-4" >
                          <h4>Update your Information</h4>
                          <b>Update Email Address:</b>
                          <b-form-input id="updateEmail"  placeholder="sarah@mail.ca"></b-form-input>
                          <b-row align-h="center" class="my-2" >
                          <b-button block variant="primary" @click="updateEmail()">Comfirm changes </b-button>
                             
                        </b-row> 
                        </b-row >
                        
                        <b-row align-h="center" class="my-1"  >    
                               
                            
                                <b>Change Passoword:</b>
                                <b-form-input id="updatePassword"  placeholder="New Passowrd"></b-form-input>
                               <b-form-input id="updatePassword1"  placeholder="Comfirm Password"></b-form-input>
                        </b-row>
                        <b-row align-h="center" class="my-2" >
                               <b-button block variant="primary" @click="updatePassword()">Comfirm changes </b-button>
                             
                        </b-row>   
                        
                    </b-col>
                   
                    <b-col>

                        <div class="mybalance">
                            <h3>Current Balance</h3>
                            <p style="font-size:125px;" >${{currentPatron.balance}}</p>
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

<script src="./userInfoViewing.js">
</script>


<style>



</style>

