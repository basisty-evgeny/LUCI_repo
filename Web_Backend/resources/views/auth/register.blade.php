@extends('layouts.app')

@section('userCss')
  <link href="assets/kcss/kregister.css" rel="stylesheet" type="text/css"/>
@endsection 

@section('content')

@if($errors->has())
    @if(count($errors) > 1)
        <div class="col-md-10 col-md-offset-2">
            <div class="alert alert-danger kerror col-md-6 col-md-offset-2">
                You need type items necessary.
            </div>             
        </div>
    @else        
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
@endif

@if (Session::has('message'))
    <div class="col-md-10 col-md-offset-2">
        <div class="alert alert-success ksuccess col-md-6 col-md-offset-2">
            {{ Session::get('message') }}
        </div>             
    </div>    
@endif

<div class="container">
    <div class="row" ">
        <div class="col-md-5 col-md-offset-3" >
            <div class="panel panel-default">
                <div class="auth-heading">Register</div>
                <div class="panel-body">
                    <form class="form-horizontal" role="form" method="POST" action="{{ url('/register') }}">
                        {!! csrf_field() !!}

                        <div class="form-group{{ $errors->has('name') ? ' has-error' : '' }}">
                            <label class="col-md-4 control-label">Name</label>

                            <div class="col-md-6">
                                <input type="text" class="form-control" name="name" value="{{ old('name') }}">

                                @if ($errors->has('name'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('name') }}</strong>
                                    </span>
                                @endif
                            </div>
                        </div>

                        <div class="form-group{{ $errors->has('email') ? ' has-error' : '' }}">
                            <label class="col-md-4 control-label">E-Mail Address</label>

                            <div class="col-md-6">
                                <input type="email" class="form-control" name="email" value="{{ old('email') }}">

                                @if ($errors->has('email'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('email') }}</strong>
                                    </span>
                                @endif
                            </div>
                        </div>

                        <div class="form-group{{ $errors->has('password') ? ' has-error' : '' }}">
                            <label class="col-md-4 control-label">Password</label>

                            <div class="col-md-6">
                                <input type="password" class="form-control" name="password">

                                @if ($errors->has('password'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('password') }}</strong>
                                    </span>
                                @endif
                            </div>
                        </div>

                        <div class="form-group{{ $errors->has('password_confirmation') ? ' has-error' : '' }}">
                            <label class="col-md-4 control-label">Confirm Password</label>

                            <div class="col-md-6">
                                <input type="password" class="form-control" name="password_confirmation">

                                @if ($errors->has('password_confirmation'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('password_confirmation') }}</strong>
                                    </span>
                                @endif
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-4">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fa fa-btn fa-user"></i>Register
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
