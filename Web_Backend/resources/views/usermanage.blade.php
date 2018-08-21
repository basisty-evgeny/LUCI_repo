@extends('layouts.app')

@section('userCss')

<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css"/>
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css"/>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css"/>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE LEVEL STYLES -->

<!-- BEGIN THEME STYLES -->
<link href="assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
<link href="assets/css/style.css" rel="stylesheet" type="text/css"/>
<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
<link href="assets/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="assets/css/pages/tasks.css" rel="stylesheet" type="text/css"/>
<link href="assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
<link href="assets/css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="favicon.ico"/>
@endsection

@section('content')
<i class="fa fa-spinner fa-pulse fa-3x fa-fw data-loading"></i>
<div  class="page-content" style="margin-left: 0px; display: none" id="main">
    <!-- BEGIN PAGE CONTENT-->
    <div class="row">
        <div class="col-md-12">

            <!-- BEGIN TABLE PORTLET-->
            <table class="table table-hover table-bordered table-scrollable" id="user_table">
            <thead>
            <tr >
                <th>
                    No
                </th>
                <th>
                    Name
                </th>
                <th>
                    Email
                </th>
                <th>
                    Login Count
                </th>
                <th>
                    Last Log time 
                </th>
                <th>
                    Create time
                </th>
                <th>
                    Update time
                </th>
                <th>
                    Setting
                </th>
            </tr>
            </thead>
            <tbody>
                @foreach ($data as $id => $mlist)
                <tr style="cursor: pointer;" data-email = "{{ $mlist->email }}">                                
                    <td style="width: 80px; text-align:center;">{{ $id + 1 }}</td>
                    <td style="width: 150px;">{{ $mlist->name }}</td>
                    <td>{{ $mlist->email }}</td>
                    <td style="width: 120px; text-align:center;"> {{ $mlist->login_count }} </td>
                    <td style="width: 120px; text-align:center;">{{ $mlist->last_logout_at}}</td>
                    <td style="width: 120px; text-align:center;">{{ $mlist->created_at }}</td>
                    <td style="width: 120px; text-align:center;">{{ $mlist->updated_at }}</td>
                    <td style="width: 100px; text-align: center;" data-id="{{ $mlist->id }}">
                        @if ($mlist->check_flag == 0) 
                        <a class="btn default btn-xs green btn-user-accept">Accept</a>
                        <a class="btn default btn-xs red btn-user-reject" style="display: none;">Reject</a>
                        @else
                        <a class="btn default btn-xs green btn-user-accept" style="display: none;">Accept</a>
                        <a class="btn default btn-xs red btn-user-reject">Reject</a>
                        @endif
                    </td>
                </tr>
                @endforeach   
            </tbody>
            </table>
            <!-- END EXAMPLE TABLE PORTLET-->
        </div>
    </div>
    <!-- END PAGE CONTENT -->

    <!-- BEGIN Accept User modal-->
    <div id="user_accept" class="modal fade" tabindex="-1" data-backdrop="static" data-keyboard="false" data-attention-animation="false">
        <div class="modal-header">
            <h4 class="modal-title">Acception</h4>
        </div>
        <div class="modal-body">
            <p>
                Would you like to accept this customer?
            </p>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn blue btn-accept-ok">Yes</button>
            <button type="button" data-dismiss="modal" class="btn btn-default btn-cancel">No</button>
        </div>
    </div>
    <!-- END Accept User modal -->

    <!-- BEGIN Reject User modal-->
    <div id="user_reject" class="modal fade" tabindex="-1" data-backdrop="static" data-keyboard="false" data-attention-animation="false">
        <div class="modal-header">
            <h4 class="modal-title">Rejection</h4>
        </div>
        <div class="modal-body">
            <p>
                Do you want to reject this customer?
            </p>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn blue btn-reject-ok">Yes</button>
            <button type="button" data-dismiss="modal" class="btn btn-default btn-cancel">No</button>
        </div>
    </div>
    <!-- END Accept User modal-->
    <!-- BEGIN Add NEW REPORT -->
    <div id="protocol_info" class="modal fade" tabindex="-1">
        <div class="modal-header">
            <h4 class="modal-title"><b>Protocol Information</b></h4>
        </div>
        <form class="form-horizontal col-md-12">
            <div class="modal-body">
                <div class="row">
                    <div class="form-body">                      
                        <div class="form-group row">
                            <label class="control-label col-md-3">Name</label>
                            <div class="col-md-8">
                                <div class="input-group disabled">
                                    <input type="text" class="form-control input-large" id="userName"/>                                    
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label col-md-3">Email</label>
                            <div class="col-md-8">
                                <div class="input-group disabled">
                                    <input type="text" class="form-control input-large" id="userEmail"/>                                   
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label col-md-3">Channel Name</label>
                            <div class="col-md-8">
                                <div class="input-group disabled">
                                    <input type="text" class="form-control input-large" id="channel_name"/>                                    

                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label col-md-3">Protocol</label>
                            <div class="col-md-8">
                                <div class="input-group disabled">
                                    <input type="text" class="form-control input-large" id="protocol"/>                                    

                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label col-md-3">Destination</label>
                            <div class="col-md-8">
                                <div class="input-group disabled">
                                    <input type="text" class="form-control input-large" id="destination"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label col-md-3">Jitter Buffer(ms)</label>
                            <div class="col-md-8">
                                <div class="input-group disabled">
                                    <input type="text" class="form-control input-large" id="jitterBuffer"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label col-md-3">Dynamic Jitter Buffer(ms)</label>
                            <div class="col-md-7">
                                <div class="input-group disabled">
                                    <input type="text" class="form-control input-large" id="dJitterBuffer"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label col-md-3">Format</label>
                            <div class="col-md-8">
                                <div class="input-group disabled">
                                    <input type="text" class="form-control input-large" id="format"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label col-md-3">Bitrate</label>
                            <div class="col-md-8">
                                <div class="input-group disabled">
                                    <input type="text" class="form-control input-large" id="bitrate"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label col-md-3">Samplerate</label>
                            <div class="col-md-8">
                                <div class="input-group disabled">
                                    <input type="text" class="form-control input-large" id="samplerate"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label col-md-3">Created Time</label>
                            <div class="col-md-8">
                                <div class="input-group disabled">
                                    <input type="text" class="form-control input-large" id="created_at"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label col-md-3">Updated Time</label>
                            <div class="col-md-8">
                                <div class="input-group disabled">
                                    <input type="text" class="form-control input-large" id="updated_at"/>
                                </div>
                            </div>
                        </div>                                                               
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" data-dismiss="modal" class="btn blue btn-save-ok"><i class="fa fa-check"></i>&nbsp OK</button>
            </div>
        </form>
    </div>
    <!-- END Add NEW REPORT -->
    <!-- END CONTENT -->
</div>
</body>

@endsection

@section('userScript')
<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script type="text/javascript" src="assets/plugins/jquery-validation/dist/jquery.validate.min.js"></script>
<script type="text/javascript" src="assets/plugins/jquery-validation/dist/additional-methods.min.js"></script>
<script src="assets/plugins/bootstrap-touchspin/bootstrap.touchspin.js" type="text/javascript"></script>

<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="assets/scripts/app.js"></script>
<script src="assets/kscripts/user-manage.js"></script>
<script>

jQuery(document).ready(function() {
    
    App.init();
    UserManage.init();
    $('#main').show();
    $('.fa.fa-spinner.fa-pulse').hide();
});

</script>

@endsection