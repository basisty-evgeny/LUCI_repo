<?php

namespace App;
use DB;
use Auth;
use App\User;
use Illuminate\Database\Eloquent\Model;

class ChannelModel extends Model
{
    protected $table = "channel";

    public function __construct() {

    }

    public function insertNewChannel($request) {

        $user = Auth::user();

        if ($user) {
            $user_id = $user->id;
            $channelName = $request->input('channel_name');

            $this->channel_name = $channelName;
            $this->user_id = $user_id;
            $this->protocol = $request->input("protocol");
            $this->destination = $request->input("destination");
            $this->jitter_buffers = $request->input("jitter_buffers");
            $this->dynamic_jitter_buffers = $request->input("dynamic_jitter_buffers");
            $this->format = $request->input("format");
            $this->bitrate = $request->input("bitrate");
            $this->samplerate = $request->input("samplerates");
            $now = date("Y-m-d H:i:s") ;
            $this->created_at = $now;
            $this->save();

            $result = array(
                'success' => true,
                'channel_name' => $this->channel_name,
                'created_at' => $now
            );

            return $result;

        }

        $result = array(
            'success' => false
        );
        return $result;
    }

    public function updateChannel($request)
    {
        $user = Auth::user();

        if ($user) {
            $user_id = $user->id;
            $channel_id = $request->input('channel_id');
            $channel = $this->find($channel_id);

            if ($channel && $channel->user_id == $user_id) {
                $channel->channel_name = $request->input("channel_name");
                $channel->user_id = $user_id;
                $channel->protocol = $request->input("protocol");
                $channel->destination = $request->input("destination");
                $channel->jitter_buffers = $request->input("jitter_buffers");
                $channel->dynamic_jitter_buffers = $request->input("dynamic_jitter_buffers");
                $channel->format = $request->input("format");
                $channel->bitrate = $request->input("bitrate");
                $channel->samplerate = $request->input("samplerates");
                $now = date("Y-m-d H:i:s") ;
                $channel->updated_at = $now;
                $channel->save();

                $result = array(
                    'success' => true,
                    'channel_name' => $channel->channel_name,
                    'updated_at' => $now
                );

                return $result;

            }

        }

        $result = array(
            'success' => false
        );
        return $result;
    }


    public function deleteChannel($request)
    {
        $user = Auth::user();

        if ($user) {
            $user_id = $user->id;
            $channel_id = $request->input('channel_id');
            $channel = $this->find($channel_id);

            if ($channel && $channel->user_id == $user_id) {
                $channel->delete();
                $result = array(
                    'success' => true
                );
                return $result;
            }
        }

        $result = array(
            'success' => false
        );
        return $result;
    }
    public function getChannels($request)
    {
        $user = Auth::user();

        if ($user) {
            $user_id = $user->id;
            $sortChannelName = $request->input('sort_channel_name');
            if(!isset($sortChannelName))
                $sortChannelName = "";

            $channels = $this->where('user_id', '=', $user_id)
                ->where('channel_name', 'LIKE', '%'.$sortChannelName.'%')
                ->get();
            $count = count($channels);

            if ($count > 0) {
                $result = array(
                    'success' => true,
                    'count'=> $count,
                    'channels' => $channels
                );
                return $result;
            }
        }

        $result = array(
            'success' => false
        );
        return $result;
    }
}
