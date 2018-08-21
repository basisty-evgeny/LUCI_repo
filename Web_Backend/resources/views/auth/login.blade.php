@extends('layouts.app')
@section('userCss')
  <link href="assets/kcss/klogin.css" rel="stylesheet" type="text/css"/>
@endsection

@section('content')
@if($errors->has())
    @foreach($errors->all() as $id => $error)
        @if($id == 0)
            <div class="col-md-10 col-md-offset-2">
                <div class="alert alert-danger kerror col-md-6 col-md-offset-2">
                    {{ $error}}
                </div>             
            </div>                
        @endif
    @endforeach        
@endif
<div class="container" style="padding-top:  150px;">
    <div class="row">
       <div class="col-md-5 col-md-offset-3" >
            <div class="panel panel-default">
                <div class="auth-heading">Login</div>
                <div class="panel-body">
                    <form class="form-horizontal" role="form" method="POST" action="{{ url('/login') }}">
                        {!! csrf_field() !!}

                        <div class="form-group{{ $errors->has('email') ? ' has-error' : '' }}">
                            <label class="col-md-3 control-label">E-Mail Address</label>

                            <div class="col-md-7">
                                <input type="email" class="form-control" name="email" value="{{ old('email') }}">

                                @if ($errors->has('email'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('email') }}</strong>
                                    </span>
                                @endif
                            </div>
                        </div>

                        <div class="form-group{{ $errors->has('password') ? ' has-error' : '' }}">
                            <label class="col-md-3 control-label">Password</label>

                             <div class="col-md-7">
                                <input type="password" class="form-control" name="password">

                                @if ($errors->has('password'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('password') }}</strong>
                                    </span>
                                @endif
                            </div>
                        </div>
                  

                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-5">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fa fa-btn fa-sign-in"></i>Login
                                </button>

                             
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
@endsection
