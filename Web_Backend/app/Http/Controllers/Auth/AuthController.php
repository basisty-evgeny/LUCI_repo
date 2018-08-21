<?php

namespace App\Http\Controllers\Auth;

use App\User;
use Validator;
use App\Http\Controllers\Controller;
use Illuminate\Foundation\Auth\ThrottlesLogins;
use Illuminate\Foundation\Auth\AuthenticatesAndRegistersUsers;
use Auth;
use Illuminate\Http\Request;

class AuthController extends Controller
{
    /*
    |--------------------------------------------------------------------------
    | Registration & Login Controller
    |--------------------------------------------------------------------------
    |
    | This controller handles the registration of new users, as well as the
    | authentication of existing users. By default, this controller uses
    | a simple trait to add these behaviors. Why don't you explore it?
    |
    */

    use AuthenticatesAndRegistersUsers, ThrottlesLogins;

    /**
     * Where to redirect users after login / registration.
     *
     * @var string
     */
    protected $redirectTo = '/';

    /**
     * Create a new authentication controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware($this->guestMiddleware(), ['except' => 'logout']);
    }

    /**
     * Get a validator for an incoming registration request.
     *
     * @param  array  $data
     * @return \Illuminate\Contracts\Validation\Validator
     */
    protected function validator(array $data)
    {
        return Validator::make($data, [
            'name' => 'required|max:255',
            'email' => 'required|email|max:255|unique:users',
            'password' => 'required|min:6|confirmed',
        ]);
    }

    /**
     * Create a new user instance after a valid registration.
     *
     * @param  array  $data
     * @return User
     */
    protected function create(array $data)
    {
        return User::create([
            'name' => $data['name'],
            'password'=>bcrypt($data['password']),
            'email' => $data['email'],
            'organization' => '',
            'activation_code' => '',
            'role_id' => 1,
            'check_flag' => 1,
            'login_count' => 0,
        ]);
    }

    public function login()
    {
        $email = $_POST['email'];
        $password = $_POST['password'];

        if (Auth::attempt(['email' => $email, 'password' => $password, 'check_flag' => 1], true)) {
            $user = User::where('email', '=', $email)->first();
            $user->login_count = $user->login_count + 1;
            $user->save();
            return redirect()->intended('/home');
        }

        return redirect()->to('/login')
            ->withInput(['email' => $email])
            ->withErrors('Login failed!');
    }


    public function register(Request $request)
    {
        $validator = $this->validator($request->all());
        $data = $request->all();

        if ($validator->fails()) {
            $this->throwValidationException(
                $request, $validator
            )->withError("Register Fail!")->withInput($data);
        }

        $this->create($request->all());


        return redirect()->to('/register')
            ->with('message', 'Legister success!');
    }
}
