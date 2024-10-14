import { WebPlugin } from '@capacitor/core';

import type { StepCounterPlugin } from './definitions';

export class StepCounterWeb extends WebPlugin implements StepCounterPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
