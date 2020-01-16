import { Color } from '@/model/game/card';

export function getColorEnumValues(): Color[] {
    return Object.keys(Color).map(key => Color[key]);
}
