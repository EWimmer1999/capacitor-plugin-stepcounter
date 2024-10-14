import { registerPlugin } from '@capacitor/core';

import type { StepCounterPlugin } from './definitions';

const StepCounter = registerPlugin<StepCounterPlugin>('StepCounter', {
  web: () => import('./web').then((m) => new m.StepCounterWeb()),
});

export * from './definitions';
export { StepCounter };
