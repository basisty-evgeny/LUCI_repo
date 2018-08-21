<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

Route::get('/', function () {
    return view('welcome');
});

Route::auth();

Route::post('/signup', 'ProxyAuthController@signup');
Route::post('/signin', 'ProxyAuthController@signin');


Route::get('/home', 'HomeController@index');

Route::post('/channel/update', 'ChannelController@updateChannel');
Route::post('/channel/create', 'ChannelController@newChannel');
Route::post('/channel/delete', 'ChannelController@deleteChannel');
Route::post('/channel/list', 'ChannelController@getChannels');

Route::resource('/users', 'UserManageController');