import { Color } from '@/store/modules/game/model';

export function getColorEnumValues(): Color[] {
    return Object.keys(Color).map(key => Color[key]);
}
