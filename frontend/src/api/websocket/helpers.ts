import { map } from 'rxjs/operators';
import { IFrame } from '@stomp/stompjs';

export const toBody = map((frame: IFrame) => frame.body);
export const toJson = map((frame: IFrame) => JSON.parse(frame.body));
