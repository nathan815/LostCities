import { Color } from './store/modules/game';

export function getColorEnumValues(): Color[] {
    return Object.keys(Color).map(key => Color[key]);
}