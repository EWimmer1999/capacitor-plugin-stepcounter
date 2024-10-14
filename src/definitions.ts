export interface StepCounterPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
