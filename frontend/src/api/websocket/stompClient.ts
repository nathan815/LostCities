import { map } from 'rxjs/operators';
import { RxStomp, RxStompConfig } from '@stomp/rx-stomp';
import { IMessage } from '@stomp/stompjs';
import { Observable } from 'rxjs';

const stompConfig: RxStompConfig = {
    connectHeaders: {
        Authentication: 'Bearer ...',
    },

    brokerURL: 'ws://localhost:8089/ws',

    debug: function(str) {
        console.log('STOMP: ' + str);
    },

    reconnectDelay: 1000,
};

const stompClient = new RxStomp();
stompClient.configure(stompConfig);
stompClient.activate();

watchJson('/topic/test').subscribe(payload => {
    console.log(payload);
});

stompClient.publish({ destination: '/app/upper', body: '{"msg":"Hello world"}' });

export function watchJson(destination: string): Observable<IMessage> {
    return stompClient.watch(destination).pipe(
        map(frame => {
            console.log('Frame Received: ', frame);
            return JSON.parse(frame.body);
        })
    );
}

export default stompClient;
