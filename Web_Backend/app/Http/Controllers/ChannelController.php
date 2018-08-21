<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Requests;
use App\ChannelModel;

class ChannelController extends Controller
{

    public function __construct()
    {

    }

    public function newChannel(Request $request)
    {
        $channel = new ChannelModel();
        $result = $channel->insertNewChannel($request);
        echo json_encode($result);
         
    }

    public function updateChannel(Request $request)
    {

        $channel = new ChannelModel();
        $result = $channel->updateChannel($request);
        echo json_encode($result);

    }

    public function deleteChannel(Request $request)
    {
        $channel = new ChannelModel();
        $result = $channel->deleteChannel($request);
        echo json_encode($result);
    }

    public function getChannels(Request $request)
    {
        $channel = new ChannelModel();
        $result = $channel->getChannels($request);
        echo json_encode($result);
    }


}
