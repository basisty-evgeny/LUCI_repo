@extends('layouts.app')

@section('content')
<div class="col-md-offset-3 col-md-5">
    <div class = 'row' >
        <button id = "tregister" style="width:100px;background: aqua; margin-top: 20px">
            register
        </button>
        <span> email : </span>
        <input type="text" id = "registerEmail" name="fname" style="width: 170px">
    </div>

    <div class = 'row' >
        <button id = "tlogin" style="width:100px;background: #ff0039;margin-top: 20px">
        login
        </button>
        <span> email : </span>
        <input type="text" id = "loginEmail" name="fname" style="width: 170px">    
    </div>
    
    <div class = 'row' >
        <button id = "tnewchannel" style="width:100px;background: aqua;margin-top: 20px">
            newchannel
        </button>
    </div>

    <div class = 'row' >

        <button id = "tupdatechannel" style="width:100px;background: #ff0039;margin-top: 20px">
            updatechannel
        </button>
        <span>channel id : </span>
        <input type="text" id = "updateChannelId" name="fname" style="width: 150px">
        
    </div>

    <div class = 'row' >
        <button id = "tdeletechannel" style="width:100px;background: aqua;margin-top: 20px">
            deletechannel
        </button>
        <span>channel id : </span>
        <input type="text" id = "deleteChannelId" name="fname" style="width: 150px">
    </div>

    <div class = 'row' >
        <button id = "tgetchannels" style="width:100px;background: #ff0039; margin-top:20px">
            getchannels
        </button>
    </div>

</div>
@endsection

@section('userScript')
<script src="ktest/ktest.js"></script>
@endsection