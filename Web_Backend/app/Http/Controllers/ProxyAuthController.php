<?php
/**
 * Created by PhpStorm.
 * User: IR
 * Date: 2018.08.13
 * Time: AM 7:03
 */

namespace App\Http\Controllers;
use Illuminate\Http\Request;
use Validator;
use Auth;
use App\User;

class ProxyAuthController extends Controller
{
    public function __construct()
    {

    }

    protected function validator(array $data)
    {
        $validatorName =  Validator::make($data, ['name' => 'required|max:255',]);
        if ($validatorName->fails())
            return 100;

        $validatorEmail =  Validator::make($data, ['email' => 'required|email|max:255|unique:users',]);
        if ($validatorEmail->fails())
            return 101;

        $validatorPassword =  Validator::make($data, ['password' => 'required|min:6',]);
        if ($validatorPassword->fails())
            return 102;

        $validatorActiveCode =  Validator::make($data, ['activation_code' => 'required|min:6',]);
        if ($validatorActiveCode->fails())
            return 103;

    }

    protected function create(array $data)
    {
        return User::create([
            'name' => $data['name'],
            'password' => bcrypt($data['password']),
            'email' => $data['email'],
            'organization' => $data['organization'],
            'activation_code' => $data['activation_code'],
            'role_id' => 0,
            'check_flag' => 1,
            'login_count' => 0,
        ]);
    }

    public function signin(Request $request)
    {
        $email = $request->email;
        $password = $request->password;

        if (Auth::attempt(['email' => $email, 'password' => $password, 'check_flag' => 1], true)) {
            echo json_encode(['success'=>true], JSON_UNESCAPED_UNICODE);
            $user = User::where('email', '=', $email)->first();
            $user->login_count = $user->login_count + 1;
            $user->save();
            return;
        }

        echo json_encode(['success'=>false], JSON_UNESCAPED_UNICODE);
    }

    public function signup(Request $request)
    {
        $validator = $this->validator($request->all());

        if ($validator >= 100) {
            $result = array(
                'success' => false,
                'reason' => $validator,
            );
            echo json_encode($result, JSON_UNESCAPED_UNICODE);
            return;
        }

        $this->create($request->all());

        echo json_encode(['success'=>true], JSON_UNESCAPED_UNICODE);

    }

}